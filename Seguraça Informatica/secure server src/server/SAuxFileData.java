package server;

public final class SAuxFileData {

	private String name;      // nome do ficheiro ex: bla.txt
	private String user;      // user que está logado
	private String owner;     // dono do ficheiro em questao
	private boolean isShared; // true se user != owner
	private String fullPath;  // caminho completo ex: server/daniel/bla.txt
	private String path;      // caminho sem o nome do ficheiro no final ex: server/daniel
	private String keyName;   // nome da chave correspondente ao ficheiro no formato owner.name.key
	
	public SAuxFileData ( String clientPath, String user ){
		String[] temp = processPath( clientPath );
		this.user = user;
		
		if( temp.length == 3 ){                          // client/daniel/bla.txt
			this.name = temp[2];
			this.path = "server/"+temp[1];
			this.fullPath = "server/"+temp[1]+"/"+temp[2];
			this.isShared = false;
			this.owner = temp[1];
		}else{                                           // client/daniel/alex/bla.txt
			this.name = temp[3];
			this.path = "server/"+temp[2];
			this.fullPath = "server/"+temp[2]+"/"+temp[3];
			this.isShared = true;
			this.owner = temp[2];
		}
		
		this.keyName = owner+"."+name+".key";
	}
	
	public String getName() {
		return name;
	}

	public String getUser() {
		return user;
	}

	public String getOwner() {
		return owner;
	}

	public boolean isShared() {
		return isShared;
	}

	public String getFullPath() {
		return fullPath;
	}

	public String getPath() {
		return path;
	}

	public String getKeyName() {
		return keyName;
	}

	private String[] processPath( String path ){
 		String temp = path.replace("\\", "/" );
 		return temp.split("/");
 	}
}
