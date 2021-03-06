package net.sf.rails.ui.swing.core;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sf.rails.game.state.Item;
import net.sf.rails.game.state.Observable;

abstract class GridField {
    
    private String text;
    private String tooltip;
    private GridColors colors;
    private GridFieldFormat format;
    
    GridField setText(String text) {
        this.text = text;
        return this;
    }
    
    GridField setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }
    
    GridField setColors(GridColors colors) {
        this.colors = colors;
        return this;
    }
    
    GridField setFormat(GridFieldFormat format) {
        this.format = format;
        return this;
    }
    
    JComponent getComponent() {
        return new JLabel();
    }
    
    abstract TableField toTableField(Item rowItem, Item colItem);
    
    final TableField.Builder buildDefaults() {
        TableField.Builder builder = TableField.builder(getComponent());
        return builder.setText(text).setTooltip(tooltip).setColors(colors).setFormat(format);
    }
    
    static GridFieldStatic createFrom(String text) {
        return new GridFieldStatic(text);
    }

    static GridFieldStatic createFrom(Observable text) {
        return new GridFieldStatic(text);
    }
    
    static GridFieldDynamic1D createFrom(Accessor1D<? extends Item> text) {
        return new GridFieldDynamic1D(text);
    }
    
    static GridFieldDynamic2D createFrom(Accessor2D<? extends Item,? extends Item> text) {
        return new GridFieldDynamic2D(text);
    }
    
}
