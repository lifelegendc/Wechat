import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import weChat.Photo;

public class Server {

	static int usersNum = 1;// Ŀǰ���û�����
	static int conmntNum = 1;// Ŀǰ�Ĺ�������Ȧ����
	static User[] u = new User[1000];
	static ConMoment[] conMoment = new ConMoment[10000];
	static User tempUser = new User();

	static void showPhotoSelection() { // չʾϵͳͷ��
		for (int i = 0; i < 4; i++) { // photo class
										// ����count�����private,������ͷ�������ֻ����д��i<4
			Photo tmp = new Photo(i);
			System.out.println(tmp);
			int c = i + 1;
			System.out.println("      " + "(" + c + ")");
		}

	}

	static void register() throws IOException {
		System.out.println("--ע��--����Ҫ��д������Ϣ��");
		boolean flag = false;// �Ƿ�������һ��unique id ��־
		String id;
		for (;;) {
			System.out.println("������id");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			id = in.readLine();
			for (int i = 0; i < Server.usersNum; i++)
				if (Server.u[i].id.equals(id)) {
					System.out.println("��id���ѱ�ע�ᣡ�����id");
				} else
					flag = true;
			if (flag)
				break;
		}
		System.out.println("����������");
		String name = "";
		BufferedReader in2 = new BufferedReader(
				new InputStreamReader(System.in));
		name = in2.readLine();
		showPhotoSelection();
		System.out.println("��ѡ��ͷ��(0/1/2/3)");
		int phto;
		BufferedReader in3 = new BufferedReader(
				new InputStreamReader(System.in));
		phto = Integer.parseInt(in3.readLine());
		u[usersNum++] = new User(id, name, phto);
		System.out.println("ע��ɹ������¼��");
	}

	static void showCommnd()

	{
		System.out.println("ע��:rgstr");
		System.out.println("��¼:lggn");
		System.out.println("�˳�ϵͳ:out");
		System.out.println("�˳���¼:lggout");
		System.out.println("��Ϣ����:post");
		System.out.println("�鿴�Լ�������Ȧ:viewMine");
		System.out.println("��ѯϵͳ�û�:srch");
		System.out.println("�Ӻ���:add");
		System.out.println("�鿴�ҵĺ���:look");
		System.out.println("�鿴ĳһ��������Ȧ:viewOne");
		System.out.println("�鿴����Ȧ�������ѷ�������:view");
		System.out.println("�鿴�������:showCommnd");
	}

	public static void main(String[] args) throws IOException {
		u[0] = new User("001", "lifelegendc", 1);
		u[0].myMoment[0] = new Moment("���ǵ�һ���û�", "�ҷ��˵�һ������Ȧ",
				"2016-11-13 08:50:51");
		u[0].mntNum = 1;
		conMoment[0] = new ConMoment("���ǵ�һ���û�", "�ҷ��˵�һ������Ȧ",
				"2016-11-13 08:50:51", u[0]);

		// �������ݲ�����,�����Ѿ���һ���û���
		System.out.println("��ӭʹ�ã�����ע�ᣡ");
		System.out.println("����������ͨ��������������ز�����");
		showCommnd();
		for (;;) {
			System.out.print("*");
			String order = "";// ������¼ÿ�ε�����
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			order = in.readLine();
			if (order.equals("rgstr"))
				register();
			else if (order.equals("lggn")) {
				System.out.println("������id��");
				String id = "";// ������¼��½��
				BufferedReader in1 = new BufferedReader(new InputStreamReader(
						System.in));
				id = in1.readLine();
				for (int i = 0; i < usersNum; i++) {
					if (u[i].id.equals(id))
						Server.tempUser = u[i];
				}
				System.out.println("���," + Server.tempUser.name);
			}
			else if (order.equals("lggout")) {
				Server.tempUser = null;
				System.out.println("�ǳ��ɹ�");
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
			else System.out.println("�������");
			

		}

		/*
		 * register(); u[1].writeMoment(); u[1].addFriend();
		 * u[1].showMyFriends(); u[0].showMyFriends(); u[1].srchFriendMoment();
		 * u[1].showConMoment(); u[0].showConMoment();
		 */
		/*
		 * ������� register();//ע�� u[3].writeMoment(); register();//
		 * 1���û��ɽ���ע�ᣬ�û���Ϣ����id��name��ͷ�� u[4].writeMoment();//
		 * 2��ע����û��ɽ�����Ϣ�������������⼰�������ݣ�ֻ֧�ַ����������ݣ��������40�֣�����Ľ��н�ȡ����
		 * u[4].showMyMoment();// �û��ɲ鿴�Լ�������Ȧ u[4].showConMoment();//
		 * 4���û��ɲ鿴����Ȧ�������ѷ��������� 'ֻ�ܲ鿴���˺��ѵ�����Ȧ u[4].search();//
		 * 3��1�����û��ɲ�ѯϵͳ�����������û� u[4].addFriend();//
		 * 3��2��.�����������û�id�Ӻ��ѣ��򻯼Ӻ��Ѳ���Ҫͬ�⣬���Զ�ͬ�����˼Ӻ��� u[4].showMyFriends();
		 * System.out.println("���ĸ��û��ĺ��Ѽӽ�ȥ��û�أ�"); u[3].showMyFriends();
		 * u[4].srchFriendMoment();// 5.�û��ɲ鿴���ѵ�����Ȧ���� u[4].showConMoment();//
		 * 4���û��ɲ鿴����Ȧ�������ѷ��������� 'ֻ�ܲ鿴���˺��ѵ�����Ȧ
		 */
	}
}
// 11.4 ���󡢷�װ
// 11.6 �����ľ��幦�ܴ���
// ����ɣ�main�������û���Ľ�������
// ��¼/ע�ᣬ��������/��֤
// �鿴�ҵ�����
// 11.12 ����ɡ�main�������û���Ľ�������\�鿴�ҵ�����\���ֺ�����������
// 11.23  ����ɡ��������,��¼/ע��
//����ɣ���������/��֤

