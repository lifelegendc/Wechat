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
	Photo myPhoto;// ���������ǻ�����Ϣ

	int mntNum;// ��ʾ�û�Ŀǰ�м�������Ȧ
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
	}// �û���Ϣչʾ�����ڹ�������Ȧ��

	public String bscData() {
		return ("\r" + myPhoto + "\r" + "name:" + name + "\r" + "ID:" + id);
	}// �û���Ϣչʾ�������û���ѯ��������ڶ���id��

	void showMyFriends()// չʾ����/ID/ͷ����б�
	{
		System.out.println("-----------My Friends------------");
		for (int i = 0; i < frdNum; i++)
			System.out.println(myFriends[i].bscData());
		System.out.println("----------------------------------");
	}

	void writeMoment() throws IOException {
		System.out.println("��������⣺");
		String title = "";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		title = in.readLine();
		System.out.println("���������ݣ�");
		String content = "";
		BufferedReader i = new BufferedReader(new InputStreamReader(System.in));
		content = i.readLine();

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateNowStr = sdf.format(d); // ��ȡ��ǰʱ��

		myMoment[mntNum++] = new Moment(title, content.substring(0), dateNowStr);
		Server.conMoment[Server.conmntNum++] = new ConMoment(title,
				content.substring(0), dateNowStr, this);

	}

	void searchOne() throws NumberFormatException, IOException {
		System.out.println("--��ѯ�û�--������id");
		String id;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		id = in.readLine();
		boolean flag = false;// �Ƿ��ҵ��û���־
		for (int i = 0; i < Server.usersNum; i++)
			if (Server.u[i].id.equals(id)) {
				flag = true;
				System.out.println(Server.u[i].bscData());
			}
		if (!flag)
			System.out.println("���û�������!");
		// �û��ɲ�ѯϵͳ�������û�������û�Ӻ��ѣ�ֻ�ܿ���΢����\ID\ͷ�񣬲��ܿ�������Ȧ��
	}

	void addFriend() throws NumberFormatException, IOException {
		// �����������û�id�Ӻ���
		System.out.println("--�Ӻ���--������id");
		String id;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		id = in.readLine();
		boolean flag = false;// �Ƿ��ҵ����ѱ�־
		for (int i = 0; i < Server.usersNum; i++)
			if (Server.u[i].id.equals(id)) {
				flag = true;
				myFriends[frdNum++] = Server.u[i];
				Server.u[i].myFriends[Server.u[i].frdNum++] = this;
				System.out.println(this.name + "��" + Server.u[i].name
						+ "���ڳ�Ϊ�˺��ѣ�");
			}

		if (!flag)
			System.out.println("���û�������!");

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
		// �ɲ鿴����Ȧ�������ѷ���������
		// ���õķ����ǣ��ڷ������б�����������Ȧ����ʱ�������Զ��˳�򣩣������������Ȧ�������û����ѵģ����ӡ
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
		System.out.println("--�鿴��������Ȧ--������id");
		String iD;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		iD = in.readLine();
		boolean flag = false;// �Ƿ��ҵ����ѱ�־

		for (int i = 0; i < frdNum; i++)
			if (myFriends[i].id.equals(iD)) {
				flag = true;
				myFriends[i].showMyMoment();
				break;
			}
		if (!flag)
			System.out.println("δ�ҵ��ú��ѣ�");
	}

}
