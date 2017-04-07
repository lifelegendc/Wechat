import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import weChat.Photo;

public class Server {

	static int usersNum = 1;// 目前的用户数量
	static int conmntNum = 1;// 目前的公共朋友圈数量
	static User[] u = new User[1000];
	static ConMoment[] conMoment = new ConMoment[10000];
	static User tempUser = new User();

	static void showPhotoSelection() { // 展示系统头像
		for (int i = 0; i < 4; i++) { // photo class
										// 里面count被设成private,读不到头像个数，只能先写成i<4
			Photo tmp = new Photo(i);
			System.out.println(tmp);
			int c = i + 1;
			System.out.println("      " + "(" + c + ")");
		}

	}

	static void register() throws IOException {
		System.out.println("--注册--您需要填写基本信息：");
		boolean flag = false;// 是否输入了一个unique id 标志
		String id;
		for (;;) {
			System.out.println("请输入id");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			id = in.readLine();
			for (int i = 0; i < Server.usersNum; i++)
				if (Server.u[i].id.equals(id)) {
					System.out.println("该id名已被注册！请更换id");
				} else
					flag = true;
			if (flag)
				break;
		}
		System.out.println("请输入姓名");
		String name = "";
		BufferedReader in2 = new BufferedReader(
				new InputStreamReader(System.in));
		name = in2.readLine();
		showPhotoSelection();
		System.out.println("请选择头像(0/1/2/3)");
		int phto;
		BufferedReader in3 = new BufferedReader(
				new InputStreamReader(System.in));
		phto = Integer.parseInt(in3.readLine());
		u[usersNum++] = new User(id, name, phto);
		System.out.println("注册成功！请登录！");
	}

	static void showCommnd()

	{
		System.out.println("注册:rgstr");
		System.out.println("登录:lggn");
		System.out.println("退出系统:out");
		System.out.println("退出登录:lggout");
		System.out.println("信息发布:post");
		System.out.println("查看自己的朋友圈:viewMine");
		System.out.println("查询系统用户:srch");
		System.out.println("加好友:add");
		System.out.println("查看我的好友:look");
		System.out.println("查看某一好友朋友圈:viewOne");
		System.out.println("查看朋友圈其他朋友发布内容:view");
		System.out.println("查看命令代号:showCommnd");
	}

	public static void main(String[] args) throws IOException {
		u[0] = new User("001", "lifelegendc", 1);
		u[0].myMoment[0] = new Moment("我是第一个用户", "我发了第一条朋友圈",
				"2016-11-13 08:50:51");
		u[0].mntNum = 1;
		conMoment[0] = new ConMoment("我是第一个用户", "我发了第一条朋友圈",
				"2016-11-13 08:50:51", u[0]);

		// 上面数据测试用,假设已经有一个用户了
		System.out.println("欢迎使用！请先注册！");
		System.out.println("现在您可以通过命令来进行相关操作：");
		showCommnd();
		for (;;) {
			System.out.print("*");
			String order = "";// 用来记录每次的命令
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			order = in.readLine();
			if (order.equals("rgstr"))
				register();
			else if (order.equals("lggn")) {
				System.out.println("请输入id名");
				String id = "";// 用来记录登陆名
				BufferedReader in1 = new BufferedReader(new InputStreamReader(
						System.in));
				id = in1.readLine();
				for (int i = 0; i < usersNum; i++) {
					if (u[i].id.equals(id))
						Server.tempUser = u[i];
				}
				System.out.println("你好," + Server.tempUser.name);
			}
			else if (order.equals("lggout")) {
				Server.tempUser = null;
				System.out.println("登出成功");
			}
			else if (order.equals("out"))
				System.exit(0);
			else if (order.equals("post"))
				tempUser.writeMoment();
			else if (order.equals("viewMine"))
				tempUser.showMyMoment();
			else if (order.equals("srch"))
				tempUser.searchOne();
			else if (order.equals("add"))
				tempUser.addFriend();
			else if (order.equals("look"))
				tempUser.showMyFriends();
			else if (order.equals("view"))
				tempUser.showConMoment();
			else if (order.equals("viewOne"))
				tempUser.srchFriendMoment();
			else if (order.equals("showCommnd"))
				showCommnd();
			else System.out.println("命令错误");
			

		}

		/*
		 * register(); u[1].writeMoment(); u[1].addFriend();
		 * u[1].showMyFriends(); u[0].showMyFriends(); u[1].srchFriendMoment();
		 * u[1].showConMoment(); u[0].showConMoment();
		 */
		/*
		 * 测试语句 register();//注册 u[3].writeMoment(); register();//
		 * 1、用户可进行注册，用户信息包括id，name，头像 u[4].writeMoment();//
		 * 2、注册的用户可进行信息发布，包括标题及文字内容，只支持发布文字内容，文字最多40字，多余的进行截取处理。
		 * u[4].showMyMoment();// 用户可查看自己的朋友圈 u[4].showConMoment();//
		 * 4、用户可查看朋友圈其他朋友发布的内容 '只能查看加了好友的朋友圈 u[4].search();//
		 * 3（1）、用户可查询系统其他的所有用户 u[4].addFriend();//
		 * 3（2）.可输入其他用户id加好友，简化加好友不需要同意，即自动同意他人加好友 u[4].showMyFriends();
		 * System.out.println("第四个用户的好友加进去了没呢？"); u[3].showMyFriends();
		 * u[4].srchFriendMoment();// 5.用户可查看朋友的朋友圈内容 u[4].showConMoment();//
		 * 4、用户可查看朋友圈其他朋友发布的内容 '只能查看加了好友的朋友圈
		 */
	}
}
// 11.4 抽象、封装
// 11.6 方法的具体功能代码
// 待完成：main函数里用户间的交互测试
// 登录/注册，输入密码/验证
// 查看我的朋友
// 11.12 【完成】main函数里用户间的交互测试\查看我的朋友\部分函数功能完善
// 11.23  【完成】命令操作,登录/注册
//待完成：输入密码/验证

