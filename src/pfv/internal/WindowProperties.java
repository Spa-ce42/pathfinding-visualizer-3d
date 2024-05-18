package pfv.internal;

public class WindowProperties {
    private int width, height;
    private String title;
    private boolean vsync;
    private String iconPath;
    private boolean pinnedToTop;

    public WindowProperties(WindowProperties wp) {
        this.width = wp.width;
        this.height = wp.height;
        this.title = wp.title;
        this.vsync = wp.vsync;
        this.iconPath = wp.iconPath;
    }

    public WindowProperties() {

    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVsync() {
        return vsync;
    }

    public void setVsync(boolean vsync) {
        this.vsync = vsync;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public boolean isPinnedToTop() {
        return this.pinnedToTop;
    }

    public void setPinnedToTop(boolean pinnedToTop) {
        this.pinnedToTop = pinnedToTop;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        WindowProperties x = (WindowProperties)super.clone();
        x.width = this.width;
        x.height = this.height;
        return x;
    }
}
