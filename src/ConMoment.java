public class ConMoment extends Moment {
	User cnmUser;

	ConMoment(String title, String content, String dat, User a) {
		super(title, content, dat);
		cnmUser = a;
	}

	public String toString() {
		return ("\r" + "user:" + cnmUser + "\t" + "title:" + title + "\t\t\t"
				+ "date:" + dat + "\r" + "content:" + content);

	}

}
