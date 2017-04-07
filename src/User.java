import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import weChat.Photo;

class User {
	static final int maxFriend = 599;
	static final int maxMoment = 100;

	String id;
	String name;
	int photoNum;
	Photo myPhoto;// 以上四行是基本信息

	int mntNum;// 表示用户目前有几条朋友圈
	int frdNum;// indicates how many friends the user get

	User[] myFriends = new User[maxFriend];
	Moment[] myMoment = new Moment[maxMoment];

	User(String id, String name, int photoNum) {
		this.id = id;
		this.name = name;
		myPhoto = new Photo(photoNum);
	}

	User() {
		;
	}

	public String toString() {
		return ("\r" + myPhoto + "\r" + "name:" + name + "\t");
	}// 用户信息展示，用在公共朋友圈里

	public String bscData() {
		return ("\r" + myPhoto + "\r" + "name:" + name + "\r" + "ID:" + id);
	}// 用户信息展示，用在用户查询里，区别在于多了id号

	void showMyFriends()// 展示姓名/ID/头像的列表
	{
		System.out.println("-----------My Friends------------");
		for (int i = 0; i < frdNum; i++)
			System.out.println(myFriends[i].bscData());
		System.out.println("----------------------------------");
	}

	void writeMoment() throws IOException {
		System.out.println("请输入标题：");
		String title = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		title = in.readLine();
		System.out.println("请输入内容：");
		String content = "";
		BufferedReader i = new BufferedReader(new InputStreamReader(System.in));
		content = i.readLine();

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d); // 获取当前时间

		myMoment[mntNum++] = new Moment(title, content.substring(0), dateNowStr);
		Server.conMoment[Server.conmntNum++] = new ConMoment(title,
				content.substring(0), dateNowStr, this);

	}

	void searchOne() throws NumberFormatException, IOException {
		System.out.println("--查询用户--请输入id");
		String id;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		id = in.readLine();
		boolean flag = false;// 是否找到用户标志
		for (int i = 0; i < Server.usersNum; i++)
			if (Server.u[i].id.equals(id)) {
				flag = true;
				System.out.println(Server.u[i].bscData());
			}
		if (!flag)
			System.out.println("该用户不存在!");
		// 用户可查询系统的所有用户（由于没加好友，只能看到微信名\ID\头像，不能看到朋友圈）
	}

	void addFriend() throws NumberFormatException, IOException {
		// 可输入其他用户id加好友
		System.out.println("--加好友--请输入id");
		String id;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		id = in.readLine();
		boolean flag = false;// 是否找到好友标志
		for (int i = 0; i < Server.usersNum; i++)
			if (Server.u[i].id.equals(id)) {
				flag = true;
				myFriends[frdNum++] = Server.u[i];
				Server.u[i].myFriends[Server.u[i].frdNum++] = this;
				System.out.println(this.name + "与" + Server.u[i].name
						+ "现在成为了好友！");
			}

		if (!flag)
			System.out.println("该用户不存在!");

	}

	void showMyMoment() {
		System.out.println("--User's Moment--");
		for (int j = mntNum - 1; j >= 0; j--) {
			System.out.println(myMoment[j]);
			System.out
					.println("------------------------------------------------------");
		}
	}

	void showConMoment() {
		// 可查看朋友圈其他朋友发布的内容
		// 采用的方法是，在服务器中遍历所有朋友圈（按时间最近到远的顺序），如果这条朋友圈是属于用户好友的，则打印
		System.out.println("--Public Moment--");
		for (int i = Server.conmntNum - 1; i >= 0; i--) {
			String conID = Server.conMoment[i].cnmUser.id;
			for (int j = 0; j < frdNum; j++) {
				if (conID.equals(myFriends[j].id))
					System.out.println(Server.conMoment[i]);

			}
		}
		System.out
				.println("-------------------------------------------------------------------------------------------");

	}

	void srchFriendMoment() throws NumberFormatException, IOException {
		System.out.println("--查看好友朋友圈--请输入id");
		String iD;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		iD = in.readLine();
		boolean flag = false;// 是否找到好友标志

		for (int i = 0; i < frdNum; i++)
			if (myFriends[i].id.equals(iD)) {
				flag = true;
				myFriends[i].showMyMoment();
				break;
			}
		if (!flag)
			System.out.println("未找到该好友！");
	}

}
