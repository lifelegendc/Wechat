package p1;

import weChat.Photo;
import java.io.*;

class Users {
	private int id;
	private String name;
	private Photo photo;
	private FriendGroup[] myFG; // record friend group content
	private int myFGCount; // number of friend groups

	Users(int id, String name, int photo) {
		this.id = id;
		this.name = name;
		this.photo = new Photo(photo);
		myFGCount = 0;
		myFG = new FriendGroup[myFGCount + 1];
	}

	public void sendFG(String title, String body) {
		myFG[myFGCount] = new FriendGroup(title, body); // add new friend group
		myFGCount++;

		FriendGroup[] temp = new FriendGroup[myFGCount + 1];
		for (int i = 0; i < myFGCount; i++) {
			temp[i] = myFG[i];
		}
		myFG = temp;
	}

	public String toString() {
		return photo.toString() + "id:" + id + "\t" + "name:" + name + "\t";
	}

	public String returnName() {
		return name;
	}

	public int returnId() {
		return id;
	}

	public void printMe() {
		System.out.println(photo.toString() + "▷ 用户id:" + id + "\t" + "用户姓名:" + name);
	}

	public void printAllFG() {
		for (int i = 0; i < myFGCount; i++) {
			System.out.println(myFG[i]);
		}
	}

}

class FriendGroup {
	String title;
	String body; // no more than 40

	FriendGroup(String title, String body) {
		this.title = title;
		if (body.length() > 40) {
			body = body.substring(0, 40);
			System.out.println("内容超过40字，已自动截断");
		}
		this.body = body;
	}

	public String toString() {
		String s;
		s = ".";
		for (int i = 0; i < body.length() + 2 || i < title.length() + 2; i++) {
			s += "-";
		}
		s += "\n| " + title + "\n| " + body + " \n`";
		for (int i = 0; i < body.length() +2 || i < title.length() + 2; i++) {
			s += "-";
		}
		return s;
	}
}

class WeChatSystem {
	Users[] users;
	int usersCount;
	int[][] usersRelation;
	Users userNow;

	WeChatSystem() {
		usersCount = 0;
		users = new Users[usersCount + 1];
		usersRelation = new int[usersCount + 1][usersCount + 1];
	}

	public int signup(String name, int photo) { // id自动生成
		users[usersCount] = new Users(usersCount + 1, name, photo);//user[i].id = i + 1
		usersCount++;
		Users[] temp = new Users[usersCount + 1];
		for (int i = 0; i < usersCount; i++) {
			temp[i] = users[i];
		}
		users = temp;

		int[][] temp2 = new int[usersCount + 1][usersCount + 1];
		for (int i = 0; i < usersCount; i++) {
			temp2[i] = usersRelation[i];
		}
		usersRelation = temp2;

		return usersCount;
	}

	public void login(int id) {
		if(0 < id && id <= usersCount){
			userNow = users[id - 1];
			System.out.println("登录成功！");
			return;
		}
		
		System.out.println("登陆id有误");
	}

	public void addFriend(int id) {
		if (userNow == null) {
			System.out.println("请先登录");
			return;
		}
		if (id == userNow.returnId()) {
			System.out.println("加不了你自己:(");
			return;
		}
		for (int i = 0; i < usersCount; i++) {
			if (id == users[i].returnId()) {
				usersRelation[userNow.returnId()][id - 1] = 1;
				System.out.println("添加成功");
				return;
			}
		}
		System.out.println("查无此id");
	}

	public void lookFG() {
		if (userNow == null) {
			System.out.println("请先登录");
			return;
		}
		int sign = 0;
		for (int i = 0; i < usersCount; i++) {
			if (usersRelation[userNow.returnId()][i] == 1) {
				sign += 1;
				if(sign == 1){
					System.out.println("您的朋友圈：\n.=============▽▽▽=============.");
				}
				users[i].printMe();
				users[i].printAllFG();
				System.out.println("`=============△△△=============`");
			}
		}
		if (sign == 0) {
			System.out.println("很遗憾的告诉你，你还没有朋友，请先添加朋友:(");
		}
	}

	public void sendFG(String title, String body) {
		if (userNow == null) {
			System.out.println("请先登录");
			return;
		}
		userNow.sendFG(title, body);
		System.out.println("发布成功！");
	}

	public String returnName() {
		return userNow.returnName();
	}

}

public class Test {

	private static void signup(WeChatSystem weChatSystem) throws IOException {
		Photo[] photos = new Photo[4];
		for (int i = 0; i < 4; i++) {
			photos[i] = new Photo(i);
		}
		WeChatSystem system = weChatSystem;
		String name;
		int photo;
		System.out.println("请输入新用户的用户名：");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		name = in.readLine();
		System.out.println("以下是可供选择的用户头像");
		for (int i = 0; i < 4; i++) {
			System.out.println(i + 1 + ":");
			System.out.println(photos[i]);
		}
		System.out.println("请选择新用户的头像：(输入数字 1 - 4 )");
		in = new BufferedReader(new InputStreamReader(System.in));
		photo = Integer.parseInt(in.readLine()) - 1;

		System.out.println("注册成功！请记住您的用户id：" + system.signup(name, photo));

	}

	private static void login(WeChatSystem weChatSystem) throws IOException {
		String id;
		WeChatSystem system = weChatSystem;
		System.out.println("请输入您的用户id：");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		id = in.readLine();
		for(int i = 0; i < id.length(); i++){
			if(!Character.isDigit(id.charAt(i))){
				System.out.println("登陆id有误!");
				return;
			}
		}
		system.login(Integer.parseInt(id));
	}

	private static void addFriend(WeChatSystem weChatSystem) throws IOException {
		int id;
		WeChatSystem system = weChatSystem;
		System.out.println("请输入您要添加的用户的用户id：");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		id = Integer.parseInt(in.readLine());
		system.addFriend(id);
	}

	private static void sendFG(WeChatSystem weChatSystem) throws IOException {
		String title, body;
		WeChatSystem system = weChatSystem;
		System.out.println("请输入朋友圈标题：");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		title = in.readLine();
		System.out.println("请输入朋友圈内容(请勿超过40字)：");
		in = new BufferedReader(new InputStreamReader(System.in));
		body = in.readLine();
		system.sendFG(title, body);
	}

	private static void lookFG(WeChatSystem weChatSystem) throws IOException {
		weChatSystem.lookFG();
	}

	public static void main(String[] args) throws IOException {
		WeChatSystem system = new WeChatSystem();
		Photo[] photos = new Photo[4];
		for (int i = 0; i < 4; i++) {
			photos[i] = new Photo(i);
		}

		for (;;) {
			if (system.usersCount == 0) {
				System.out.println("▼请注册新用户");
				signup(system);
			} else if (system.userNow == null) {
				System.out.println("▼1.注册新用户；2.登录用户；3.退出WeChat");
				System.out.println("请输入数字：");
				String num;
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				num = in.readLine();
				switch (num) {
				case "1":
					signup(system);
					break;
				case "2":
					login(system);
					break;
				case "3":
					return;
				default:
					System.out.println("输入有误，请重新输入");
				}
			} else {
				System.out.println("▼当前用户：" + system.returnName());
				System.out.println("1.注册新用户；2.添加好友；3.发朋友圈；4.查看好友朋友圈；5.退出登录；6.退出WeChat");
				System.out.println("请输入数字：");
				String num;
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				num = in.readLine();
				switch (num) {
				case "1":
					signup(system);
					break;
				case "2":
					addFriend(system);
					break;
				case "3":
					sendFG(system);
					break;
				case "4":
					lookFG(system);
					break;
				case "5":
					system.userNow = null;
					break;
				case "6":
					return;
				default:
					System.out.println("输入有误，请重新输入");
				}
			}

		}

	}

}
