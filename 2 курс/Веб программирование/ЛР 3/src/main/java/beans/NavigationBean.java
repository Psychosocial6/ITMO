package beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named("navigationBean")
@RequestScoped
public class NavigationBean {

    public String navigateMain() {
        return "go-main";
    }

    public String navigateIndex() {
        return "go-index";
    }
}