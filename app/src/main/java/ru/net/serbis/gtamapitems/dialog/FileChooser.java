package ru.net.serbis.gtamapitems.dialog;

import android.app.*;
import android.content.*;
import android.widget.*;
import java.io.*;
import ru.net.serbis.gtamapitems.adapter.*;

public abstract class FileChooser extends AlertDialog.Builder implements DialogInterface.OnClickListener
{
    private ListView list;
	private FilesAdapter adapter;

	public FileChooser(
        Context context,
        int title,
        boolean onlyFolder,
        boolean onlyFile,
        File folder,
        String ext)
	{
		super(context);

		list = new ListView(context);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		adapter = new FilesAdapter(context, this, onlyFolder, onlyFile, folder, ext);
		list.setAdapter(adapter);
		list.setOnItemClickListener(adapter);
		adapter.initFiles();

		setTitle(title);
		setView(list);
		setPositiveButton(android.R.string.ok,this);
		setNegativeButton(android.R.string.cancel, this);
		show();
	}

    public FileChooser(Context context, int title, boolean onlyFolder, boolean onlyFile)
	{
        this(context, title, onlyFolder, onlyFile, null, null);
    }
    
    public void onClick(DialogInterface dialog, int id)
    {
        switch(id)
        {
            case Dialog.BUTTON_POSITIVE:
                positive();
                break;
        }
    }

    private void positive()
    {
        File file = adapter.getSelected();
        if (file != null)
        {
            onChoose(file.getAbsolutePath());
        }
    }

	public abstract void onChoose(String path);

    public ListView getList()
    {
        return list;
    }
}
