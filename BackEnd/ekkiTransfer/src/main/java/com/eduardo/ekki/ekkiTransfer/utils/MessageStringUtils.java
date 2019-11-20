package com.eduardo.ekki.ekkiTransfer.utils;

import com.eduardo.ekki.ekkiTransfer.common.MessageStrings;

public class MessageStringUtils {
	
	public static String formatSingle(MessageStrings message, String param) {
		return String.format(message.get(), param);
	}
	
	public static String formatDual(MessageStrings message, String ... param ) {
		return String.format(message.get(), param[0], param[1]);
	}

}
