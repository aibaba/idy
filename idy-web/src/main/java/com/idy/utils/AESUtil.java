package com.idy.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static byte[] encrypt(String content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, String password) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(password.getBytes()));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return result; // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String ss = "四川新闻网成都7月17日讯（何若鸿 记者张肇婷）7月16日凌晨，一奔驰车驾驶员醉酒驾车在成都市中心将一辆出租车撞坏后逃逸，跑了约3公里后被出租车司机拦下。随后闹剧继续上演，醉驾者袭击民警、两进派出所……7月17日，该驾驶员因涉嫌醉酒驾驶，危险驾驶等被刑拘，目前该案还在进一步调查中。";
		ss =ss+"奔驰车肇事逃逸 司机一身酒气 “车身猛地一晃，车子从中间车道被撞偏至左侧车道。”7月16日凌晨2时54分，出租车司机黄伟搭载乘客潘先生，从阳光春天前往冻青树街。沿顺城大街行驶至银河王朝酒店门口时，右侧的人行辅道突然冲出一辆车牌号为桂ANR6**的白色奔驰越野车，撞上黄伟驾驶的出租车右侧。";
		ss =ss+"“奔驰车停都没停一下，加速驶离了现场。”于是黄伟一边驱车紧随，一边让后座的乘客打电话报警。";
		ss =ss+"“我跟着奔驰车追了大约3公里，才追上。”黄伟回忆，奔驰车围着银河王朝大酒店，经西御龙街、人民中路，从政府街逆行一段后，兜了一圈回到顺城大街。在银河王朝大酒店门口，白色奔驰车掉了个头，往太升南路驶去，后拐进了一家酒店的停车场。";
		ss =ss+"“当时车内仅有驾驶员一人，而且他身上还有一股酒味。”黄伟将奔驰司机从车上拉了下来，要求他赔偿车辆损失。这时，太升南路派出所民警、成都市交警四分局事故大队民警王虎相继赶到现场。";
		ss =ss+"司机打“醉拳” 民警被打脑震荡";
		ss =ss+"随后，的哥黄伟和乘客、奔驰车驾驶员被带往太升南路派出所调查后，移交给了成都市交警四分局事故大队。";
		ss =ss+"凌晨5时40分，民警王虎开着警车，搭载三人前往西区医院抽血。经检验，奔驰车驾驶员王某血液中酒精含量达255.6mg/100ml，已达醉酒驾驶标准。“我找了代驾，不可能酒驾，没有酒驾！”奔驰车驾驶员王某反复的吼着，而事实上从头至尾车里都只有他一个人。";
		ss =ss+"在抽完血准备离开时，王某的母亲樊女士赶来了，“你走开，不要管我！”王某大声叫骂，不停挣扎，一把推开母亲，迅速跑向民警王虎身后，向其脑袋挥出一拳，王虎倒地。这还没完，王某又是飞起一脚，踹在王虎背上，母亲樊女士也被撞到在地。樊女士一边哭喊，一边连声赔不是。“对不起，对不起。”";
		ss =ss+"因民警被袭击受伤，黄伟随后又被带到了茶店子派出所接受调查。而民警王虎则被送往金牛区人民医院接受检查。";
		ss =ss+"“他本来还比较配合，但他母亲出现后，他情绪突然激动。”王虎说，“他（王某）在车上跟我说，你今天不给我面子，不给我机会，我会让你记住我的。”经医生诊断，王虎出现轻微脑震荡症状，需留院观察。";
		ss =ss+"奔驰车涉嫌走私套牌 肇事者被刑拘";
		ss =ss+"据了解，奔驰驾驶员王某今年24岁，大学毕业后工作3年多了，从事金融行业工作。昨（16）日下午16时，四川新闻网记者在成都市交警四分局询问室内见到了王某，他身高约1米7，身着蓝色短袖T恤、碎花裤衩。对于酒后所发生的一切，王某仍未回过神。";
		ss =ss+"“昨天晚上心情很不好，因为最近的工作一直都很不顺，就出去喝了点酒，我最多就喝了一瓶红酒。”王某告诉四川新闻网记者，他平时很少喝酒，朋友喝酒他都在边上看着。“重点是喝酒结束后，我不记得我怎么从KTV出来的。”王某说，车是从朋友那里借来的，准备过两天去机场接人。";
		ss =ss+"经调查，民警发现王某所驾驶的白色奔驰GL450越野车市价160余万元，但“来路不明”，涉嫌走私。其所悬挂的车牌号桂ANR6**为一辆凌志轿车所有。因涉嫌醉酒后驾驶机动车，危险驾驶罪，今日凌晨2时许，王某已被警方刑事拘留。“对其涉嫌套牌、事故逃逸、袭警等罪名，将做进一步调查，后续工作将移交相关单位处理。”办案民警介绍。";
		ss =ss+"对话肇事者：";
		ss =ss+"不记得肇事袭警";
		ss =ss+"四川新闻网记者：还记得凌晨发生了什么？";
		ss =ss+"王某：我不晓得什么时候喝完酒出来的，也不记得跟出租车发生擦挂，之后的事也完全没有印象。";
		ss =ss+"四川新闻网记者：为什么会跑到太升南路的酒店去？";
		ss =ss+"王某：我也不晓得，我当时离家很近，在东门上，应该直接回家的。我也不知道为什么跑到酒店去。";
		String password = "12345678";  
		//加密   
		System.out.println("加密前：" + ss);  
		byte[] encryptResult = encrypt(ss, password);  
		System.out.println(encryptResult.length);
		//解密   
		byte[] decryptResult = decrypt(encryptResult,password);  
		System.out.println("解密后：" + new String(decryptResult)); 
	}

}
