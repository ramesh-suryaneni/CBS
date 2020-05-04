package com.imagination.cbs.util;

public class AdobeConstant {

	private AdobeConstant() {
		// default constructor ignored
	}

	public static final String FILE_EXTENSION = ".pdf";
	public static final String ADOBE = "ADOBE";
	public static final String ADOBE_ACCESS_TOKEN = "ADOBE_ACCESS_TOKEN";
	public static final String ADOBE_REFRESH_TOKEN = "ADOBE_REFRESH_TOKEN";
	public static final String ADOBE_TOKEN_TYPE = "ADOBE_TOKEN_TYPE";
	public static final String ADOBE_ACCESS_TOKEN_EXP_TIME = "ADOBE_ACCESS_TOKEN_EXP_TIME";
	public static final String ADOBE_API_BASE_URI = "ADOBE_API_BASE_URI";
	public static final String ADOBE_OAUTH_BASE_URL = "ADOBE_OAUTH_BASE_URL";
	public static final String ACCESS_POINT = "api/rest/v6";
	public static final String ROLE = "role";
	public static final String ORDER = "order";
	public static final String TRANSIENT_DOCUMENT_ID = "transientDocumentId";
	public static final String SIGNATURETYPE = "signatureType";
	public static final String STATE = "state";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String FILEINFOS = "fileInfos";
	public static final String PARTICIPANTSETSINFO = "participantSetsInfo";
	public static final String MEMBERINFOS = "memberInfos";

	public static final String SIGNATURE_ESIGN = "ESIGN";
	public static final String ID = "id";
	public static final String STATE_IN_PROCESS = "IN_PROCESS";
	public static final String ADOBE_AUTH_CODE = "ADOBE_AUTH_CODE";
	public static final String ADOBE_REDIRECT_URL = "ADOBE_REDIRECT_URL";

	public static final String OAUTH_ACCESS_TOKEN_ENDPOINT = "/token";
	public static final String OAUTH_CODE_ACCESS_ENDPOINT = "/oauth";
	public static final String OAUTH_REFRESH_TOKEN_ENDPOINT = "/refresh";
	public static final String BEARER = "Bearer ";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String TOKEN_TYPE = "token_type";
	public static final String EXPIRES_IN = "expires_in";

	public static final String ADOBE_CLIENT_ID = "ADOBE_CLIENT_ID";
	public static final String ADOBE_CLIENT_SECRET = "ADOBE_CLIENT_SECRET";
	public static final String ADOBE_REDIRECT_URI = "ADOBE_REDIRECT_URL";
	public static final String ADOBE_GRANT_TYPE = "authorization_code";

	public static final String GRANT_TYPE = "grant_type";
	public static final String CLIENT_ID = "client_id";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String REDIRECT_URI = "redirect_uri";
	public static final String CODE = "code";

	public static final String AGREEMENTS_ENDPOINT = "/agreements";
	public static final String AGREEMENTS_COMBINEDDOCUMENT = "/agreements/{agreementId}/combinedDocument";
	public static final String TRANSIENT_DOCUMENTS_ENDPOINT = "/transientDocuments";

	public enum HttpHeaderField {
		CONTENT_TYPE("Content-Type"), AUTHORIZATION("Authorization"), FILE_NAME("File-Name"), FILE("File"),
		MIME_TYPE("Mime-Type"), USER_EMAIL("X-User-Email"), ACCEPT("ACCEPT");

		private final String fieldName;

		HttpHeaderField(String fieldName) {
			this.fieldName = fieldName;
		}

		@Override
		public String toString() {
			return fieldName;
		}
	}

}
