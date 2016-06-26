package br.com.jorgepgjr.entity;

import org.springframework.hateoas.ResourceSupport;

/**
 * Error message structure to use on REST errors responses
 * @author jorge
 *
 */
public class ErrorInfo extends ResourceSupport{
    public final String url;
    public final String msg;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.msg = ex.getLocalizedMessage();
    }

	public ErrorInfo(String url, String msg) {
		this.url = url;
		this.msg = msg;
	}
    
}