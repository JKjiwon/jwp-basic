package core.mvc;

import core.mvc.view.View;

public class ModelAndView {
    private ModelMap model;
    private View view;

    public ModelAndView(ModelMap model, View view) {
        this.model = model;
        this.view = view;
    }

    public ModelAndView(View view) {
        this.view = view;
        this.model = new ModelMap();
    }

    public ModelMap getModel() {
        return model;
    }

    public View getView() {
        return view;
    }
}
