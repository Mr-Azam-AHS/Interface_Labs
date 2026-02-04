//(c) A+ Computer Science
//www.apluscompsci.com
//Name -

import static java.lang.System.*;

public interface Monster_i {
	public int getHowBig();

	public String getName();

	public boolean isBigger(Monster other);

	public boolean isSmaller(Monster other);

	public boolean namesTheSame(Monster other);
}