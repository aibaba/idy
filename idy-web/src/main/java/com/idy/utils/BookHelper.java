package com.idy.utils;

public class BookHelper {
	
	//作品频道映射工具
	public static String channelMapper(int channel){
		String name = "";
		
		switch (channel){
			case 1:
				name="男频";
				break;
			case 2:
				name="女频";
				break;
			default:
		}
		return name;
	}
	
	//作品分类映射工具
	public static String typeMapper(int genre){
		String name = "";
		
		switch (genre){
		case 11:
			name = "悬疑灵异";
			break;
		case 12:
			name = "都市社会";
			break;
		case 13:
			name = "玄幻奇幻";
			break;
		case 14:
			name = "武侠仙侠";
			break;
		case 15:
			name = "军事历史";
			break;
		case 16:
			name = "科幻游戏";
			break;
		case 17:
			name = "同人周边";
			break;
		case 18:
			name = "体育竞技";
			break;
			
		case 21:
			name = "现代言情";
			break;
		case 22:
			name = "古代言情";
			break;
		case 23:
			name = "青春校园";
			break;
		case 24:
			name = "穿越架空";
			break;
		case 25:
			name = "玄幻仙侠";
			break;
		case 26:
			name = "女强女尊";
			break;
		case 27:
			name = "家斗种田";
			break;
		case 28:
			name = "娱乐偶像";
			break;
		}
		return name;
	}
}
