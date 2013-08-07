package fr.oni.cookbook;

import java.util.ArrayList;
import java.util.List;

import fr.oni.cookbook.model.Recipe;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

public class EditActivity extends ActionBarActivity implements TabListener {

	List<Fragment> fragList = new ArrayList<Fragment>();
	Recipe recipe;

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// Do nothing
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment f = null;
		if (fragList.size() > tab.getPosition()) {
			fragList.get(tab.getPosition());
		}
		Bundle data = new Bundle();
		data.putSerializable("recipe", recipe);

        if (f == null) {
			switch (tab.getPosition()) {
				case 0:
					f = new EditTitleFragment();
					break;

				default:
					break;
			}
			fragList.add(f);
		}
		f.setArguments(data);
        ft.replace(android.R.id.content, f);

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		 if (fragList.size() > tab.getPosition()) {
	            ft.remove(fragList.get(tab.getPosition()));
	        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.edit_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		recipe = (Recipe) getIntent().getExtras().getSerializable("recipe");

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(recipe.getTitle());

		Tab tabRecipe = actionBar.newTab().setText(getString(R.string.tab_name_recipe)).setTabListener(this);

		actionBar.addTab(tabRecipe);

	}

}
