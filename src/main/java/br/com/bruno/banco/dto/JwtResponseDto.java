package br.com.bruno.banco.dto;

public class JwtResponseDto {
		private String token;

		public JwtResponseDto(String token) {
			this.token = token;
		}

		public String getToken() {
			return this.token;
		}

	}

