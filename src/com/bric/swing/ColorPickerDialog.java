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

import javax.swing.JButton;
import javax.swing.JDialog;

import corny.Utils.InteractionAdapters.CancelKeyListener;

/**
 * This wraps a <code>ColorPicker</code> in a simple dialog with "OK" and
 * "Cancel" options.
 * <P>
 * (This object is used by the static calls in <code>ColorPicker</code> to show
 * a dialog.)
 * 
 */
public class ColorPickerDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private boolean canceled = true;
	private ColorPicker cp;
	ActionListener buttonListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			setVisible(false);
		}
	};

	public ColorPickerDialog(Color color,
			boolean showExpertControls, boolean includeOpacity, boolean hideColorPanel) {
		initialize(color, showExpertControls, includeOpacity, hideColorPanel);
	}

	private void initialize(Color color,
			boolean showExpertControls, boolean includeOpacity, boolean hideColorPanel) {
		cp = new ColorPicker(showExpertControls, includeOpacity, hideColorPanel, null);
		setResizable(false);
		setModalityType(ModalityType.APPLICATION_MODAL);
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

		c.gridy++;
		c.weightx = 1;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 5, 0);
		JButton cancelButton = new JButton("Abbrechen");
		cancelButton.setFocusable(false);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canceled = true;
				setVisible(false);
			}
		});
		cp.addKeyListener(new CancelKeyListener(cancelButton));
		getContentPane().add(cancelButton, c);

		c.gridx++;
		c.weightx = 0;
		c.weighty = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(0, 0, 5, 5);
		JButton okButton = new JButton("OK");
		okButton.setFocusable(false);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				setVisible(false);
			}
		});
		getRootPane().setDefaultButton(okButton);
		getContentPane().add(okButton, c);

		pack();
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

	public boolean isCanceled() {
		return canceled;
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			// Ensure state if dialog is opened multiple times
			canceled = true;
		}
		super.setVisible(b);
	}

	public ColorPicker getColorPicker() {
		return cp;
	}
}
