package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.filtros.ClasificacionFiltro;
import ar.uba.fi.armariovirtual.modelo.filtros.OpcionFiltro;

public class AdaptadorFiltro extends BaseExpandableListAdapter
{
    private Context _context;
    private List<ClasificacionFiltro>  _clasificaciones;

    /**
     * Constructor
     * @param context Contexto donde se utiliza la lista de clasificaciones
     * @param clasificaciones Lista de clasificaciones que tiene que mostrar la vista actual
     */
    public AdaptadorFiltro(Context context, List<ClasificacionFiltro> clasificaciones) {
        _context = context;
        _clasificaciones = clasificaciones;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return _clasificaciones.get(listPosition).getOpcionesFiltro().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return (long)( listPosition*1024+expandedListPosition );  // Max 1024 children per group
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = LayoutInflater.from( _context ).inflate(R.layout.filtros_opcion, parent, false);

        final OpcionFiltro opcionFiltro = (OpcionFiltro)getChild( groupPosition, childPosition );

        final View finalV = v;
        final int finalGroupPosition = groupPosition;
        final int finalChildPosition = childPosition;

        final CheckBox cb = v.findViewById( R.id.check );
        cb.setChecked( opcionFiltro.isSeleccionada() );
        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                opcionFiltro.setSeleccionada(cb.isChecked());
                ExpandableListView listView = (ExpandableListView)finalV.getParent();
                if(listView != null)
                {
                    long packedPosition = ExpandableListView.getPackedPositionForChild(finalGroupPosition, finalChildPosition);
                    int flatListPosition = listView.getFlatListPosition(packedPosition);
                    listView.performItemClick(finalV, flatListPosition, getChildId(finalGroupPosition, finalChildPosition));
                }
            }
        });

        TextView nombre = v.findViewById( R.id.nombre );
        nombre.setText(opcionFiltro.getNombre());
        nombre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                cb.performClick();
            }
        });

        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return _clasificaciones.get( groupPosition ).getOpcionesFiltro().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return _clasificaciones.get( groupPosition );
    }

    @Override
    public int getGroupCount() {
        return _clasificaciones.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return (long)( groupPosition*1024 );  // To be consistent with getChildId
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = null;
        if( convertView != null )
            v = convertView;
        else
            v = LayoutInflater.from( _context ).inflate(R.layout.filtros_clasificacion, parent, false);

        ClasificacionFiltro clasificacionFiltro = (ClasificacionFiltro)getGroup( groupPosition );

        TextView nombre = v.findViewById( R.id.nombre );
        nombre.setText(clasificacionFiltro.getNombre());
        return v;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupCollapsed (int groupPosition) {}

    @Override
    public void onGroupExpanded(int groupPosition) {}

}
