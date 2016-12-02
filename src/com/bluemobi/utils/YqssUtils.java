package com.bluemobi.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gson.Gson;

import net.sf.json.JSONObject;

public class YqssUtils {

	public static final int LEAP_YEAR_DAYS = 366;
	public static final int NO_LEAP_YEAR_DAYS = 365;

	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 将json格式的字符串解析成Map对象
	 * <li>json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> jsontoMap(Object object) {
//		Map<String, Object> data = new HashMap<String, Object>();
//		// 将json字符串转换成jsonObject
//		JSONObject jsonObject = JSONObject.fromObject(object);
//		Iterator it = jsonObject.keys();
//		// 遍历jsonObject数据，添加到Map对象
//		while (it.hasNext()) {
//			String key = String.valueOf(it.next());
//			Object value = (Object) jsonObject.get(key);
//			data.put(key, value);
//		}
//		return data;
		return new Gson().fromJson(object.toString(), Map.class);
	}
	
	public static void main(String[] args) {
		String text = "{\"title\":\"梦\",\"content\":\"打抛铮亮还留着微微热度的麦克风躺在盒子里，散发檀香味，金属丝弦紧绷的吉他倚在桌旁，舞台后场区一位两鬓斑白的老者抿着茶水，靠在椅子上，安逸地眯着眼，想想刚才的表演，嘴角浮出了笑容，又拿起茶杯，品味着岁月的悠扬。" + 
        "空荡冷清的房子里，窗缝窜着风，打着哨儿，侵蚀着小屋积攒的热气。一位少年躺在床上，呆呆盯着天花板，偶尔眉头颦蹙，长吁口气，闭上双眼，又陷入了沉思，他望着桌上摆着的高考冲刺书，又用手紧紧握起耳麦戴上，播放着喜欢的歌曲，人生的一个抉择在这个高二寒假进行着。是乖乖靠文化分考上大学然后过着丰衣足食，平凡简单的生活？还是追求自己的热爱―音乐，能让自己有朝一日站在万众瞩目的舞台上，有自己的音乐团队，让自己的声音，自己的名字为人们所熟悉，即使未来命归西天，也有很多陌生人知道曾经有这样一个人存在过！他为艺术贡献好多，牺牲好多！以至于未来的某天某个人能在舞台上表演他的作品，那么他的生命就活出了意义，活出了价值。"
                           + "没错，那个少年最后的决定是音乐，寒假结束后，他一人骑着单车，全身裹着厚厚的棉衣，穿梭于小城的几个中学，寻找她的音乐启蒙老师。终于，那个人出现了，敦实的体型被带有淡淡烟丝味的一身黑衣包住，一双炯良的眼睛透过镜片更显得明亮，爽朗的笑声更是深深吸引了他，仿佛是上天注定，他们见第一面之后便一拍即合，整整聊了一个下午，诉心事话衷肠，就感觉像是一对父子一样。"
        +"少年的音乐旅程就这样开始了，老师在课堂上耐心辅导着他，帮他找到学习某些知识的捷径，要学的真的好多好多，少年也烦恼苦闷过，但还有三个学习音乐的小伙伴和他一起哭一起笑，每天教室里回荡着他们的声音，晚上学校外的一家面馆准时会有他们吃面条的哧溜声，还有每周放假后周围的一条小吃街就成了他们的天下，放肆地玩，尽情地吃成为他们的宗旨，最难忘的是那天晚上几个人在学校楼顶上的那次酣畅淋漓，然后对着天空呐喊自己的名字和梦想，再不疯狂我们就老了！没人在以后还会把你当孩子看的，你需要的是自立自制，才能融入这个灯红酒绿的社会。"
                        + "  就这样，他们一起成长，一起学习了将近半年，迎来了艺考，考试期间他们第一次离开了家，吃在一起，住在一起，形影不离，欢乐化解了考试的紧张焦躁，半个月里几乎每天都在考试，艺考现场人特别多，摩肩接踵，背着重重画板的美术生，穿着单薄只是裹了件大号黑羽绒的舞蹈生，背着各种乐器拿着乐谱的我们，徘徊在考场内外，报名点上、考点上都排着都排着很长的队伍，有时候地方不够，直接折个弯再继续排，有时甚至排一个上午才能进入考场考试，考生们挤在一起相互取暖，寒嘘问候，真的好温暖！他们几个在半个月的考试中发挥着自己的最佳水平，互相勉励，争取在最后交给老师让自己满意的答卷，他们最想听到的是大学录取通知书拿到手上老师那爽朗的笑声。几个月后，一伙人冲进老师的办公室，和老师抱成一团，又哭又笑，老师手里握着被泪水打湿的四份通知书，僵硬地垂在空中，那几分钟应该是他们最快乐的时刻了吧！\",\"userId\":\"951716\",\"postBarId\":\"25\",\"token\":\"gNG6A6WSrom-vGQtQQiCZQ1uDhQyt9Ode7Z9ewn_\"}";
		
		Map<String,Object> map = jsontoMap(text);
		System.out.println(map.get("content"));
	}	
}
