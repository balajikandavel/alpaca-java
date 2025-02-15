package io.github.mainstringargs.alpaca.rest;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class AlpacaRequestBuilder.
 */
public class AlpacaRequestBuilder {

    /** The Constant URL_SEPARATOR. */
    public final static String URL_SEPARATOR = "/";

    /** The url parameters. */
    public final Map<String, String> urlParameters = new LinkedHashMap<String, String>();

    /** The body properties. */
    public final Map<String, String> bodyProperties = new LinkedHashMap<String, String>();

    /** The base url. */
    private final String apiVersion;

    /** The base url. */
    private final String baseUrl;

    /** The appended endpoints. */
    private List<String> appendedEndpoints = new ArrayList<String>();

    /** The default endpoint. */
    private boolean defaultEndpoint = true;

    /** The endpoint. */
    private String endpoint;

    /**
     * Instantiates a new alpaca request builder.
     *
     * @param apiVersion the api version
     * @param baseUrl    the base url
     * @param endpoint   the endpoint
     */
    public AlpacaRequestBuilder(String apiVersion, String baseUrl, String endpoint) {
        this.apiVersion = apiVersion;
        this.baseUrl = baseUrl;
        this.endpoint = endpoint;
    }

    /**
     * Append URL parameter.
     *
     * @param parameterKey   the parameter key
     * @param parameterValue the parameter value
     */
    public void appendURLParameter(String parameterKey, String parameterValue) {
        if (parameterValue != null) {
            urlParameters.put(parameterKey, parameterValue);
        }
    }

    /**
     * Append body property.
     *
     * @param parameterKey   the parameter key
     * @param parameterValue the parameter value
     */
    public void appendBodyProperty(String parameterKey, String parameterValue) {
        if (parameterValue != null) {
            bodyProperties.put(parameterKey, parameterValue);
        }
    }

    /**
     * Checks if is default endpoint.
     *
     * @return true, if is default endpoint
     */
    public boolean isDefaultEndpoint() {
        return defaultEndpoint;
    }

    /**
     * Sets the default endpoint.
     *
     * @param defaultEndpoint the new default endpoint
     */
    public void setDefaultEndpoint(boolean defaultEndpoint) {
        this.defaultEndpoint = defaultEndpoint;
    }

    /**
     * Gets the body as JSON.
     *
     * @return the body as JSON
     */
    public String getBodyAsJSON() {
        JsonObject jsonBody = new JsonObject();

        for (Entry<String, String> entry : bodyProperties.entrySet()) {
            jsonBody.addProperty(entry.getKey(), entry.getValue());
        }

        return jsonBody.toString();
    }

    /**
     * Append endpoint.
     *
     * @param endpoint the endpoint
     */
    public void appendEndpoint(String endpoint) {
        if (endpoint != null) {
            appendedEndpoints.add(endpoint);
        }

    }

    /**
     * Gets the endpoint.
     *
     * @return the endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Gets the url.
     *
     * @return the url
     */
    public String getURL() {
        StringBuilder builder = new StringBuilder(baseUrl);
        builder.append(URL_SEPARATOR);
        builder.append(apiVersion);

        if (defaultEndpoint) {
            builder.append(URL_SEPARATOR);
            builder.append(getEndpoint());
        }

        for (String endpoint : appendedEndpoints) {
            builder.append(URL_SEPARATOR);
            builder.append(endpoint);
        }

        if (!urlParameters.isEmpty()) {
            builder.append('?');

            for (Entry<String, String> entry : urlParameters.entrySet()) {
                builder.append(entry.getKey().trim());
                builder.append('=');
                builder.append(entry.getValue().trim());
                builder.append('&');
            }
            // removes last &
            builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }
}
