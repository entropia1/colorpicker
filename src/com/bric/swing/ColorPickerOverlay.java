/*
 * @(#)ColorPickerDialog.java  1.0  2008-03-01
 *
 * Copyright (c) 2008 Jeremy Wood
 * E-mail: mickleness@gmail.com
 * All rights reserved.
 *
 * The copyright of this software is owned by Jeremy Wood.
 * You may not use, copy or modify this software, except in
 * accordance with the license agreement you entered into with
 * Jeremy Wood. For details see accompanying license terms.
 */
package com.bric.swing;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeListener;

/**
 * This wraps a <code>ColorPicker</code> in a simple dialog with "OK" and
 * "Cancel" options.
 * <P>
 * (This object is used by the static calls in <code>ColorPicker</code> to show
 * a dialog.)
 * 
 */
public class ColorPickerOverlay extends JFrame {

	private static final long serialVersionUID = 1L;

	private ColorPicker cp;
	ActionListener buttonListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	};

	public ColorPickerOverlay(Color color,
			boolean showExpertControls, boolean includeOpacity, boolean hideColorPanel,
			ChangeListener externalChangeListener) {
		initialize(color, showExpertControls, includeOpacity, hideColorPanel,
				externalChangeListener);
	}

	private void initialize(Color color, boolean showExpertControls,
			boolean includeOpacity, boolean hideColorPanel,
			ChangeListener externalChangeListener) {
		getRootPane().putClientProperty("Window.style", "small");
		setAlwaysOnTop(true);
		// setFocusableWindowState(false);
		cp = new ColorPicker(showExpertControls, includeOpacity,
				hideColorPanel, externalChangeListener);
		setResizable(false);
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(10, 10, 10, 0);
		getContentPane().add(cp, c);
		cp.setRGB(color.getRed(), color.getGreen(), color.getBlue());
		cp.setOpacity(((float) color.getAlpha()) / 255f);
		pack();
	}
	
	public ColorPicker getColorPicker() {
		return cp;
	}

	public void setColor(Color c) {
		cp.setColor(c);
	}

	/**
	 * @return the color committed when the user clicked 'OK'. Note this returns
	 *         <code>null</code> if the user canceled this dialog, or exited via
	 *         the close decoration.
	 */
	public Color getColor() {
		return cp.getColor();
	}
}