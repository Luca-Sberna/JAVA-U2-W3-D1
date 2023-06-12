package JAVAU2W2PROJECT.auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import JAVAU2W2PROJECT.entities.User;
import JAVAU2W2PROJECT.exceptions.UnauthorizedException;
import JAVAU2W2PROJECT.services.UsersService;
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
		String authHeader = request.getHeader("Authorization");
		if (authHeader == null || !authHeader.startsWith("Bearer "))
			throw new UnauthorizedException("Per favore aggiungi il token all'authorization header");
		String accessToken = authHeader.substring(7);

		// verifica la scadenza del token
		JWTTools.isTokenValid(accessToken);

		// se esiste estraggo l email e cerco l utente
		String email = JWTTools.extractSubject(accessToken);
		System.out.println("******************************** " + email);

		// try catch per completare l autorizzazione con ulteriori info e aggiunta al
		// context
		try {
			User user = usersService.findByEmail(email);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
			filterChain.doFilter(request, response);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// evita che il filtro venga eseguito per ogni richiesta
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}