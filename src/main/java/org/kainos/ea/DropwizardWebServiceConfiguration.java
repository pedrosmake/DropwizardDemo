package org.kainos.ea;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class DropwizardWebServiceConfiguration extends Configuration {
	@Valid
	@NotNull
	private final SwaggerBundleConfiguration swagger = new SwaggerBundleConfiguration();

	@JsonProperty("swagger")
	public SwaggerBundleConfiguration getSwagger() {
		swagger.setResourcePackage("org.kainos.ea.controllers");
		String[] schemes = { "http", "https" };
		swagger.setSchemes(schemes);
		return swagger;
	}
}
