/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.scriblon.plugins.chancecraft.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Logger;
import org.bukkit.plugin.PluginManager;

/**
 * Coppied auto-downloader for bukkitcontrib.
 * @author Coen
 */
public class Downloader {

    public static void check(Logger log, PluginManager pm) {   
        if (pm.getPlugin("BukkitContrib") == null) {
            try {
                Downloader.download(log, new URL("http://bit.ly/autoupdateBukkitContrib"), new File("plugins/BukkitContrib.jar"));
                pm.loadPlugin(new File("plugins" + File.separator + "BukkitContrib.jar"));
                pm.enablePlugin(pm.getPlugin("BukkitContrib"));
            } catch (final Exception ex) {
                log.warning("[LogBlock] Failed to install BukkitContrib, you may have to restart your server or install it manually.");
            }
        }
    }

    public static void download(Logger log, URL url, File file) throws IOException {

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        final int size = url.openConnection().getContentLength();
        log.info("Downloading " + file.getName() + " (" + size / 1024 + "kb) ...");
        final InputStream in = url.openStream();
        final OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        final byte[] buffer = new byte[1024];
        int len, downloaded = 0, msgs = 0;
        final long start = System.currentTimeMillis();
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
            downloaded += len;
            if ((int) ((System.currentTimeMillis() - start) / 500) > msgs) {
                log.info((int) ((double) downloaded / (double) size * 100d) + "%");
                msgs++;
            }
        }
        in.close();
        out.close();
        log.info("Download finished");
    }
}
