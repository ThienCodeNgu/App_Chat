package Model;

public class Account {

	private String fullname;
	private int port;

	public Account(String fullname, int port) {
		this.fullname = fullname;
		this.port = port;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String toString() {
		return "Tài khoản: " + fullname + " " + port;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Account acc = (Account) obj;
		return fullname.equals(acc.fullname);
	}

}
