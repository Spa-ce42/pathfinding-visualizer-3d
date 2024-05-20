package pfv.internal;

/**
 * The type Window properties.
 */
public class WindowProperties {
    private int width, height;
    private String title;
    private boolean vsync;
    private String iconPath;
    private boolean pinnedToTop;

    /**
     * Instantiates a new Window properties.
     *
     * @param wp the wp
     */
    public WindowProperties(WindowProperties wp) {
        this.width = wp.width;
        this.height = wp.height;
        this.title = wp.title;
        this.vsync = wp.vsync;
        this.iconPath = wp.iconPath;
    }

    /**
     * Instantiates a new Window properties.
     */
    public WindowProperties() {

    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets width.
     *
     * @param width the width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets height.
     *
     * @param height the height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Is vsync boolean.
     *
     * @return the boolean
     */
    public boolean isVsync() {
        return vsync;
    }

    /**
     * Sets vsync.
     *
     * @param vsync the vsync
     */
    public void setVsync(boolean vsync) {
        this.vsync = vsync;
    }

    /**
     * Gets icon path.
     *
     * @return the icon path
     */
    public String getIconPath() {
        return iconPath;
    }

    /**
     * Sets icon path.
     *
     * @param iconPath the icon path
     */
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    /**
     * Is pinned to top boolean.
     *
     * @return the boolean
     */
    public boolean isPinnedToTop() {
        return this.pinnedToTop;
    }

    /**
     * Sets pinned to top.
     *
     * @param pinnedToTop the pinned to top
     */
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
