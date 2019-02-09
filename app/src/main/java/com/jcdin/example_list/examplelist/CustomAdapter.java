package com.jcdin.example_list.examplelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

class NotesAdapter extends ArrayAdapter<Note> {

    private ArrayList<Note> notes;
    private ArrayList<Note> allNotes;
    private Filter customFilter;

    public NotesAdapter(Context context, int textViewResourceId, ArrayList<Note> notes) {
        super(context, textViewResourceId, notes);;
        this.notes = notes;
        this.allNotes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.listitemlayout, null);
        }
        Note note = notes.get(position);
        // format how to display each note
        if (note != null) {
            TextView tt = (TextView) view.findViewById(R.id.toptext);
            TextView bt = (TextView) view.findViewById(R.id.bottomtext);
            if (tt != null) {
                tt.setText("Title: " + note.getName());                            }
            if(bt != null){
                bt.setText("Description: " + note.getDescription());
            }
        }
        return view;
    }

    @Override
    public int getCount() {
        return this.notes.size();
    }

    public void resetData() {
        this.notes = allNotes;
    }

    @Override
    public Filter getFilter() {
        if (customFilter == null)
            customFilter = new CustomFilter();
        return customFilter;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            // if the is no filter query then return the original list of notes
            // otherwise filter list of notes that matches the name
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = allNotes;
                results.count = allNotes.size();
            }
            else {
                ArrayList<Note> filteredNotes = new ArrayList<Note>();
                // here we filter notes by `name` (UPPERCASED)
                for (Note n : allNotes) {
                    if (n.getName().toUpperCase().startsWith(constraint.toString().toUpperCase())) {
                        filteredNotes.add(n);
                    }
                }
                results.values = filteredNotes;
                results.count = filteredNotes.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // Now we have to inform the adapter about the new list filtered
            // update notes to store the filtered results
            notes = (ArrayList<Note>) results.values;
            notifyDataSetChanged();

        }
    }
}
