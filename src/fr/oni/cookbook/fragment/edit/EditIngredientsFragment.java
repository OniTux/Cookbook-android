package fr.oni.cookbook.fragment.edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import fr.oni.cookbook.R;
import fr.oni.cookbook.StringConstant;
import fr.oni.cookbook.dialog.edit.EditDialogListener;
import fr.oni.cookbook.dialog.edit.EditIngredientDialogFragment;
import fr.oni.cookbook.model.Data;
import fr.oni.cookbook.model.Ingredient;

public class EditIngredientsFragment extends Fragment implements EditDialogListener{

	Data data;
	ArrayAdapter<Ingredient> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		data = (Data) getActivity().getApplicationContext();
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ActionBarActivity activity = (ActionBarActivity) getActivity();
		activity.supportInvalidateOptionsMenu();
		ListView v = (ListView) inflater.inflate(R.layout.edit_recipe_ingredients, container, false);
		adapter = new ArrayAdapter<Ingredient>(getActivity(), R.layout.ingredients_edit_list_linear_layout, R.id.ingredient_edit_text, data.getRecipes().get(data.getPosition()).getIngredients());
		v.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
				EditIngredientDialogFragment dialog = new EditIngredientDialogFragment();
				dialog.setListener(EditIngredientsFragment.this);
				Bundle data = new Bundle();
				data.putInt(StringConstant.KEY_POSITION_INGREDIENT, pos);
				dialog.setArguments(data);
				dialog.show(getFragmentManager(), StringConstant.TAG_EDIT_INGREDIENT);
			}

		});

		v.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> adapterView, final View v, final int position, final long itemID) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(R.string.delete_dialog_ingredient_title);
				builder.setMessage(R.string.delete_dialog_ingredient_text);
				builder.setPositiveButton(R.string.delete_dialog_yes, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						data.getRecipes().get(data.getPosition()).getIngredients().remove(position);
						adapter.notifyDataSetChanged();
						dialog.dismiss();
					}

				});
				builder.setNegativeButton(R.string.delete_dialog_no, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}

				});

				builder.show();
				return false;
			}
		});

		v.setAdapter(adapter);
		return v;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_add_ingredient:
				addIngredient();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void addIngredient() {
		data.getRecipes().get(data.getPosition()).getIngredients().add(new Ingredient(getString(R.string.new_ingredient)));
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		MenuItem item = menu.findItem(R.id.action_add_ingredient);
		item.setVisible(true);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onCloseDialog() {
		adapter.notifyDataSetChanged();
	}
}
