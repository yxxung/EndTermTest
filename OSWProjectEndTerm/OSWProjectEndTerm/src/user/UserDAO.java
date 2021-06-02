package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn;//�����ͺ��̽��� �����ϰ� ���ִ� �ϳ��� ��ü
	private PreparedStatement pstmt;//
	private ResultSet rs;//������ ���� �� �ִ� ��ü
	
	public UserDAO() {//mysql�� ������ �ϰ� ����,�ڵ����� �����ͺ��̽� Ŀ�ؼ��� �Ͼ
		try {//����ó��
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID="root";
			String dbPasseord="parkms00*";
			Class.forName("com.mysql.jdbc.Driver");//mysql����̹��� ã�´�.
			//����̹��� mysql�� ������ �� �ֵ��� �Ű�ü ������ �ϴ� �ϳ��� ���̺귯��
			conn=DriverManager.getConnection(dbURL,dbID,dbPasseord);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int login(String userID, String userPassword) {//�α����� �õ��ϴ� �Լ�
		String SQL="SELECT userPassword FROM USER WHERE userID = ?";
		try {
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1,userID);//���̵� ��ġ�ϸ� ��й�ȣ�� �����´�.
			rs=pstmt.executeQuery();//rs�� ������ ����� �־��ش�.
			if(rs.next()) {//����� �����Ѵٸ�
				if(rs.getString(1).equals(userPassword))//sql������ �����ؼ� ���� ����� ������ �õ��ߴ� passwd��
					return 1;//�α��� ����
				else
					return 0; //��й�ȣ ����ġ
			}
			return -1;//���̵� ����,rs�� ����� ���� ���� ����
		} catch(Exception e) {
			e.printStackTrace();
		}
		return -2; //�����ͺ��̽� ����
	}

	
	public int join(User user) {
		String SQL = "INSERT INTO USER VALUES (?,?,?,?)";//�� �װ��� ���� �� �� �ֵ��� �Ѵ�.
		try {//insert������ ����� 0�̻��� ���ڰ� �����Ǳ� ������ -1�� �ƴѰ��� �������� ȸ�������� �̷�����.
			pstmt=conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserEmail());
			return pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();//����ó��
		}
		return -1;//�����ͺ��̽� ����
	}
}