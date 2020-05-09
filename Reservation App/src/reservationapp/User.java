package reservationapp;

public abstract class User {
			// properties
		
			private String email;
			private String userName;
			private String password;
			
			// methods
			
			public String getEmail()
			{
				return email;
			}
			
			public String getUserName()
			{
				return userName;	
			}
			
			public String getPassword()
			{
				return password;
			}
			
			public void setEmail( String email)
			{
				this.email = email;
			}
			
			public void setUserName( String name)
			{
				this.userName = name;
			}
			
			public void setPassword( String password)
			{
				this.password = password;
			}
}
