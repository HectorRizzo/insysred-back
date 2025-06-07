package com.insysred.isp.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SubnetCalculator {

	private static final String IP_REGEX = 
	        "^(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\." +
	        "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\." +
	        "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\." +
	        "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$";
	
	private static final Pattern IP_PATTERN = Pattern.compile(IP_REGEX);
	
	public static boolean isValidIp(String ip) {
        if (ip == null) {
            return false;
        }
        Matcher matcher = IP_PATTERN.matcher(ip);
        return matcher.matches();
    }
	
	public static int getDecimalNumber(int binaryNumber){
        int decimalNumber = 0;
        int power = 0;
        while(binaryNumber > 0){
            int temp = binaryNumber%10;
            decimalNumber += temp*Math.pow(2, power);
            binaryNumber = binaryNumber/10; 
            power++;
        }
        return decimalNumber;
    }
		 
	public static  String calcMask(Integer mask) {
		StringBuilder result = new StringBuilder("");
		if (mask != null) {
			if(mask == 1) mask = 1;
			if(mask > 30) mask = 30;
			StringBuilder sbOct = new StringBuilder("");
			for(int ii = 1; ii <= 32; ii++) {
				if(ii <= mask) {
					sbOct.append("1");
				}else {
					sbOct.append("0");
				}
				if(ii > 0 && ii%8 == 0) {
					result.append(getDecimalNumber(Integer.valueOf(sbOct.toString()).intValue()));
					if(ii < 32) {
						result.append(".");
					}
					sbOct = new StringBuilder("");
				} 
			}
		}
		
		return result.toString();
	}
 
    public static List<String> calculateIPsInSubnet(String ip, String subnetMask) throws UnknownHostException {
        List<String> ipAddresses = new ArrayList<>();

        if (ip != null && subnetMask != null) {
        	int[] ipAddr = toIntArray(InetAddress.getByName(ip).getAddress());
            int[] maskAddr = toIntArray(InetAddress.getByName(subnetMask).getAddress());

            int[] networkAddr = new int[4];
            int[] broadcastAddr = new int[4];

            for (int i = 0; i < 4; i++) {
                networkAddr[i] = ipAddr[i] & maskAddr[i];
                broadcastAddr[i] = networkAddr[i] | (~maskAddr[i] & 0xFF);
            }

            int[] currentAddr = networkAddr.clone();

            while (!isEqual(currentAddr, broadcastAddr)) {
                ipAddresses.add(toIpString(currentAddr));
                increment(currentAddr);
            }
            ipAddresses.add(toIpString(broadcastAddr));
        }
        return ipAddresses;
    }

    private static int[] toIntArray(byte[] bytes) {
        int[] ints = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            ints[i] = bytes[i] & 0xFF;
        }
        return ints;
    }

    private static void increment(int[] addr) {
        for (int i = addr.length - 1; i >= 0; i--) {
            if (addr[i] < 255) {
                addr[i]++;
                break;
            } else {
                addr[i] = 0;
            }
        }
    }

    private static boolean isEqual(int[] addr1, int[] addr2) {
        for (int i = 0; i < addr1.length; i++) {
            if (addr1[i] != addr2[i]) {
                return false;
            }
        }
        return true;
    }

    private static String toIpString(int[] addr) {
        return addr[0] + "." + addr[1] + "." + addr[2] + "." + addr[3];
    }
	
    public static String convertToCIDRNotation(String subnetMask) throws UnknownHostException {
        InetAddress maskAddress = InetAddress.getByName(subnetMask);
        int cidrPrefixLength = getCIDRPrefixLength(maskAddress);
        return String.valueOf(cidrPrefixLength);
    }

    private static int getCIDRPrefixLength(InetAddress mask) {
        byte[] maskBytes = mask.getAddress();
        int prefixLength = 0;
        boolean zeroFound = false;

        for (byte b : maskBytes) {
            int bitMask = 0x80;
            for (int i = 0; i < 8; i++) {
                if ((b & bitMask) != 0) {
                    if (zeroFound) {
                        throw new IllegalArgumentException("Invalid subnet mask.");
                    }
                    prefixLength++;
                } else {
                    zeroFound = true;
                }
                bitMask >>>= 1;
            }
        }
        return prefixLength;
    }
}
