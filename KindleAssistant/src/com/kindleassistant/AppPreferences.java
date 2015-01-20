package com.kindleassistant;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.kindleassistant.utils.Base64;
import com.kindleassistant.utils.LogUtil;

public class AppPreferences {
	private static final int IV_SIZE = 16;
	private static final int KEY_SIZE = 32;
	private static final String AES_ALGORITHM = "AES/CBC/PKCS7Padding";
	private static final String AES_KEY_TYPE = "AES";
	private static final String RANDOM_GEN_ALGORITHM = "SHA1PRNG";

	public static final String SHAREDPREFERENCES_NAME = "sharedperences";
	public static SharedPreferences sPreferences;
	static {
		sPreferences = PreferenceManager.getDefaultSharedPreferences(App
				.getContext());
	}

	// 保存离线下载文件的日期 用于判断之前的离线缓存是否删除

	private static final String IS_REGISTERPUSH = "is_registerpush";
	private static final String HAS_SLIDINGGUIDE = "has_slidingguide";
	private static final String USER_EMAIL = "user_email";
	private static final String APPUID = "AppUid";

	protected static byte[] generateKey(int len) {
		byte result[] = new byte[len];
		long l = 0x807d950L;
		for (int j = 0; j < len; j++) {
			l = (l * 0x3c88596cL) % 0x100000000L;
			result[j] = (byte) (int) (l % 256L);
		}

		return result;
	}

	public final static byte[] generateIV(int len) {
		byte bytes[] = new byte[len];
		try {
			SecureRandom.getInstance(RANDOM_GEN_ALGORITHM).nextBytes(bytes);
			return bytes;
		} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
			LogUtil.s(nosuchalgorithmexception.getMessage());
			return null;
		}
	}

	protected static String secureValue(String s) {
		if (s == null)
			return null;

		byte iv[];
		SecretKeySpec secretkeyspec;
		IvParameterSpec ivparameterspec;
		byte key[] = generateKey(KEY_SIZE);
		try {
			iv = generateIV(IV_SIZE);
			secretkeyspec = new SecretKeySpec(key, AES_KEY_TYPE);
			ivparameterspec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretkeyspec, ivparameterspec);
			byte code[] = cipher.doFinal(s.getBytes("utf-8"));
			byte secret[] = new byte[code.length + IV_SIZE];
			System.arraycopy(iv, 0, secret, 0, IV_SIZE);
			System.arraycopy(code, 0, secret, IV_SIZE, code.length);
			return new String(Base64.encode(secret), "ASCII");

		} catch (NoSuchAlgorithmException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (NoSuchPaddingException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (InvalidKeyException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (InvalidAlgorithmParameterException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (IllegalBlockSizeException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (BadPaddingException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (UnsupportedEncodingException e) {
			LogUtil.s(e.getMessage());
			return null;
		}
	}

	protected static String valueFromSecureValue(String s) {
		if (TextUtils.isEmpty(s))
			return null;

		byte aesCode[];
		SecretKeySpec secretkeyspec;
		IvParameterSpec ivparameterspec;
		byte key[] = generateKey(KEY_SIZE);
		byte code[] = Base64.decode(s);
		byte iv[] = new byte[16];
		try {
			System.arraycopy(code, 0, iv, 0, IV_SIZE);
			int aesLen = code.length - IV_SIZE;
			aesCode = new byte[aesLen];
			System.arraycopy(code, IV_SIZE, aesCode, 0, aesLen);
			secretkeyspec = new SecretKeySpec(key, AES_KEY_TYPE);
			ivparameterspec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secretkeyspec, ivparameterspec);
			return new String(cipher.doFinal(aesCode), "utf-8");
		} catch (NoSuchAlgorithmException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (NoSuchPaddingException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (InvalidKeyException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (InvalidAlgorithmParameterException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (IllegalBlockSizeException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (BadPaddingException e) {
			LogUtil.s(e.getMessage());
			return null;
		} catch (UnsupportedEncodingException e) {
			LogUtil.s(e.getMessage());
			return null;
		}
	}

	/**
	 * 是否显示过引导页
	 * 
	 * @return
	 */
	public static boolean getHasSlidingGuide() {
		return sPreferences.getBoolean(HAS_SLIDINGGUIDE, false);
	}

	public static boolean setHasSlidingGuide(boolean value) {
		return sPreferences.edit().putBoolean(HAS_SLIDINGGUIDE, value).commit();
	}

	/**
	 * 是否注册信鸽推送
	 * 
	 * @return
	 */
	public static boolean getRegisterPush() {
		return sPreferences.getBoolean(IS_REGISTERPUSH, false);
	}

	public static boolean setRegisterPush(boolean value) {
		return sPreferences.edit().putBoolean(IS_REGISTERPUSH, value).commit();
	}

	public static String getAppUid() {
		return valueFromSecureValue(sPreferences.getString(APPUID, ""));
	}

	public static boolean saveAppUid(String value) {
		return sPreferences.edit().putString(APPUID, secureValue(value))
				.commit();
	}
	
	public static String getEmail() {
		return sPreferences.getString(USER_EMAIL, "");
	}

	public static boolean setEmail(String value) {
		return sPreferences.edit().putString(USER_EMAIL, value).commit();
	}

}
