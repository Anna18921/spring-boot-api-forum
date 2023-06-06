package br.com.alura.forum.config.validacao;

public class ErrorFormDto {
	private String field;
	private String erro;
	
	
	public ErrorFormDto(String field, String erro) {
		this.field = field;
		this.erro = erro;
	}
	
	public String getField() {
		return field;
	}
	public String getErro() {
		return erro;
	}
	
	
}
