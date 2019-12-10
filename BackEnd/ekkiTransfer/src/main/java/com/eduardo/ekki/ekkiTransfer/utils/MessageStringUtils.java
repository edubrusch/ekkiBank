package com.eduardo.ekki.ekkiTransfer.utils;

import com.eduardo.ekki.ekkiTransfer.common.MessageStringsEnum;

public class MessageStringUtils {
	
	public static String formatSingle(MessageStringsEnum message, String param) {
		return String.format(message.get(), param);
	}
	
	public static String formatDual(MessageStringsEnum message, String ... param ) {
		return String.format(message.get(), param[0], param[1]);
	}

}
