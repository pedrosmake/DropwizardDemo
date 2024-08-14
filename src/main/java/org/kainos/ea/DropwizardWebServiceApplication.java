package org.kainos.ea;

import java.security.Key;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.kainos.ea.auth.JwtAuthenticator;
import org.kainos.ea.auth.RoleAuthoriser;
import org.kainos.ea.controllers.AuthController;
import org.kainos.ea.controllers.CustomerController;
import org.kainos.ea.controllers.OrderController;
import org.kainos.ea.controllers.ProductController;
import org.kainos.ea.daos.AuthDao;
import org.kainos.ea.daos.CustomerDao;
import org.kainos.ea.daos.OrderDao;
import org.kainos.ea.daos.ProductDao;
import org.kainos.ea.models.JwtToken;
import org.kainos.ea.services.AuthService;
import org.kainos.ea.services.CustomerService;
import org.kainos.ea.services.OrderService;
import org.kainos.ea.services.ProductService;
import org.kainos.ea.validators.OrderValidator;
import org.kainos.ea.validators.ProductValidator;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.jsonwebtoken.Jwts;

public class DropwizardWebServiceApplication extends Application<DropwizardWebServiceConfiguration> {

	public static void main(final String[] args) throws Exception {
		new DropwizardWebServiceApplication().run(args);
	}

	@Override
	public String getName() {
		return "DropwizardWebService";
	}

	@Override
	public void initialize(final Bootstrap<DropwizardWebServiceConfiguration> bootstrap) {
		bootstrap.addBundle(new SwaggerBundle<DropwizardWebServiceConfiguration>() {
			@Override
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
					DropwizardWebServiceConfiguration configuration) {
				return configuration.getSwagger();
			}
		});
	}

	@Override
	public void run(final DropwizardWebServiceConfiguration configuration, final Environment environment) {
		Key jwtKey = Jwts.SIG.HS256.key().build();

		environment.jersey()
				.register(new AuthDynamicFeature(
						new OAuthCredentialAuthFilter.Builder<JwtToken>().setAuthenticator(new JwtAuthenticator(jwtKey))
								.setAuthorizer(new RoleAuthoriser()).setPrefix("Bearer").buildAuthFilter()));
		environment.jersey().register(RolesAllowedDynamicFeature.class);
		environment.jersey().register(new AuthValueFactoryProvider.Binder<>(JwtToken.class));
//        environment.jersey().register(new JsonProcessingExceptionMapper(true));

		environment.jersey().register(new OrderController(new OrderService(new OrderDao(), new OrderValidator())));
		environment.jersey()
				.register(new ProductController(new ProductService(new ProductDao(), new ProductValidator())));
		environment.jersey().register(new CustomerController(new CustomerService(new CustomerDao())));

		environment.jersey().register(new AuthController(new AuthService(new AuthDao(), jwtKey)));
	}

}
