package JAVAU2W3D1.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import JAVAU2W3D1.entities.User;
import JAVAU2W3D1.exceptions.UnauthorizedException;
import JAVAU2W3D1.services.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	UsersService usersService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// estraggo e aggiungo il token all header altrimenti lancio un eccezione
		if (!request.getMethod().equalsIgnoreCase("OPTIONS")) {
			// 0. Questo metodo verrà invocato per ogni request
			// 1. Prima di tutto dovrò estrarre il token dall'Authorization Header
			String authHeader = request.getHeader("Authorization");

			if (authHeader == null || !authHeader.startsWith("Bearer "))
				throw new UnauthorizedException("Per favore aggiungi il token all'authorization header");

			String accessToken = authHeader.substring(7);

			// 2. Verifico che il token non sia stato nè manipolato nè sia scaduto
			JWTTools.isTokenValid(accessToken);

			// 3. Se OK

			// 3.0 Estraggo l'email dal token e cerco l'utente
			String email = JWTTools.extractSubject(accessToken);

			User user;
			try {
				user = usersService.findByEmail(email);
				// 3.1 Aggiungo l'utente al SecurityContextHolder

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
						user.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				System.out.println(authToken);

				SecurityContextHolder.getContext().setAuthentication(authToken);

				// 3.2 puoi procedere al prossimo blocco della filterChain
				filterChain.doFilter(request, response);
			} catch (org.springframework.data.crossstore.ChangeSetPersister.NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// 4. Se non OK -> 401 ("Per favore effettua di nuovo il login")
		} else {
			filterChain.doFilter(request, response);
		}

	}

	// evita che il filtro venga eseguito per ogni richiesta
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}