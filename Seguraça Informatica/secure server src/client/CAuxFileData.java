package client;

public final class CAuxFileData {

	private String name;      // nome do ficheiro ex: bla.txt
	private String user;      // user que está logado
	private String owner;     // dono do ficheiro em questao
	private boolean isShared; // true se user != owner
	private String fullPath;  // caminho completo ex: client/daniel/bla.txt ou client/daniel/alex/bla.txt
	private String path;      // caminho sem o nome do ficheiro no final ex: client/daniel ou client/daniel/alex
	private String keyName;   // nome da chave correspondente ao ficheiro no formato owner.name.key
	private String sigName;   // nome da assinatura correspondente ao ficheiro no formato user.name.sig
	
	public CAuxFileData( String commandLineArg, String user ){
		this.user = user;
		
		if( commandLineArg.split("/").length == 2 ){          // alex/bla.txt
			this.name = commandLineArg.split("/")[1];
			this.owner = commandLineArg.split("/")[0];
			isShared = true;
			this.fullPath = "client/"+user+"/"+owner+"/"+name;
			this.path = "client/"+user+"/"+owner;
		}else{                                                // bla.txt
			this.name = commandLineArg;
			this.owner = user;
			isShared = false;
			this.fullPath = "client/"+user+"/"+name;
			this.path = "client/"+user;
		}
		
		this.keyName = owner+"."+name+".key";
		this.sigName = user+"_"+name+".sig";
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
	
	public String getSigName() {
		return sigName;
	}
}
