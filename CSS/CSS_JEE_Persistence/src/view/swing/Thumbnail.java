package view.swing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;

import services.viewer.Viewer;

class Thumbnail implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -8106458510487944660L;
	
	public final ImageIcon image;
	public final File file;
	private transient Viewer viewer;
	
	public Thumbnail (File file, ImageIcon image, Viewer viewer) {
		this.file = file;
		this.image = image;
		this.viewer = viewer;
	}
	
	public void save () throws IOException {
		ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(this);
		out.close();
	}
	
	public static Thumbnail load (File file) throws IOException {
		ObjectInput in = new ObjectInputStream(new FileInputStream(file));
		Thumbnail tb = null;
		try {
			tb = (Thumbnail) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		in.close();
		return tb;
	}

	public Viewer getViewer() {
		return viewer;
	}
	
	public void setViewer(Viewer v) {
		viewer = v;
	}
}
