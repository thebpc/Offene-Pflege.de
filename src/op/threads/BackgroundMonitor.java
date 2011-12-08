/*
 * OffenePflege
 * Copyright (C) 2008 Torsten Löhr
 * This program is free software; you can redistribute it and/or modify it under the terms of the 
 * GNU General Public License V2 as published by the Free Software Foundation
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General 
 * Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program; if not, write to 
 * the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA
 * www.offene-pflege.de
 * ------------------------ 
 * Auf deutsch (freie Übersetzung. Rechtlich gilt die englische Version)
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der GNU General Public License, 
 * wie von der Free Software Foundation veröffentlicht, weitergeben und/oder modifizieren, gemäß Version 2 der Lizenz.
 *
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen von Nutzen sein wird, aber 
 * OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder der VERWENDBARKEIT FÜR EINEN 
 * BESTIMMTEN ZWECK. Details finden Sie in der GNU General Public License.
 *
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm erhalten haben. Falls nicht, 
 * schreiben Sie an die Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110, USA.
 * 
 */
package op.threads;

import entity.EntityTools;
import entity.system.SYSMessagesTools;
import op.OPDE;

import java.util.Date;

/**
 * @author tloehr
 */
public class BackgroundMonitor extends Thread {

    //long loginid;
    boolean interrupted;

    int zyklen = 0;

    /**
     *
     */
    public BackgroundMonitor() {
        super();
        this.interrupted = false;
        this.setName("BackgroundMonitor");
    }

    /**
     *
     */
    public void run() {
        while (!interrupted) {
            zyklen++;

            if (zyklen % 6 == 0 || zyklen == 1) {   // Sofort und alle 6 Durchgänge
                OPDE.getHost().setLpol(new Date());
                EntityTools.merge(OPDE.getHost());
            }

            OPDE.debug("Background Monitor Zyklus: "+zyklen);

            SYSMessagesTools.processSystemMessage();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException ie) {
                OPDE.getLogger().debug("BackgroundMonitor INTERRUPTED !");
                interrupted = true;
            }
        }
    }
}