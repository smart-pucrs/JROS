import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class msgParser {
	public void loadMsg(String s){
		StringBuffer sb = new StringBuffer("");
		File msgFile = new File(s);
		FileInputStream is;
		int ch;
		try{
			is = new FileInputStream(msgFile);
			while((ch = is.read()) != -1)
				sb.append((char)ch);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
}
