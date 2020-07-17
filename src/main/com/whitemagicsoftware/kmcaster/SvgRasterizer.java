/*
 * Copyright 2020 White Magic Software, Ltd.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.whitemagicsoftware.kmcaster;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Map;

import static java.awt.RenderingHints.*;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

/**
 * Responsible for converting SVG images into rasterized PNG images.
 */
public class SvgRasterizer {
  public final static Map<Object, Object> RENDERING_HINTS = Map.of(
      KEY_ANTIALIASING,
      VALUE_ANTIALIAS_ON,
      KEY_ALPHA_INTERPOLATION,
      VALUE_ALPHA_INTERPOLATION_QUALITY,
      KEY_COLOR_RENDERING,
      VALUE_COLOR_RENDER_QUALITY,
      KEY_DITHERING,
      VALUE_DITHER_DISABLE,
      KEY_FRACTIONALMETRICS,
      VALUE_FRACTIONALMETRICS_ON,
      KEY_INTERPOLATION,
      VALUE_INTERPOLATION_BICUBIC,
      KEY_RENDERING,
      VALUE_RENDER_QUALITY,
      KEY_STROKE_CONTROL,
      VALUE_STROKE_PURE,
      KEY_TEXT_ANTIALIASING,
      VALUE_TEXT_ANTIALIAS_ON
  );

  private final static SVGUniverse sRenderer = new SVGUniverse();

  /**
   * Rasterizes a vector graphic to a given size using a {@link BufferedImage}.
   * The rendering hints are set to produce high quality output.
   *
   * @param path   Fully qualified path to the image resource to rasterize.
   * @param dstDim The output image dimensions.
   * @return The rasterized {@link Image}.
   * @throws SVGException Could not open, read, parse, or render SVG data.
   */
  public Image rasterize( final String path, final Dimension dstDim )
      throws SVGException {
    final var diagram = loadDiagram( path );
    final var wDiagram = diagram.getWidth();
    final var hDiagram = diagram.getHeight();
    final var srcDim = new Dimension( (int) wDiagram, (int) hDiagram );

    final var scaled = fit( srcDim, dstDim );
    final var wScaled = (int) scaled.getWidth();
    final var hScaled = (int) scaled.getHeight();

    final var image = new BufferedImage( wScaled, hScaled, TYPE_INT_ARGB );

    final var g = image.createGraphics();
    g.setRenderingHints( RENDERING_HINTS );

    final var transform = g.getTransform();
    transform.setToScale( wScaled / wDiagram, hScaled / hDiagram );

    g.setTransform( transform );
    diagram.render( g );
    g.dispose();

    return image;
  }

  /**
   * Gets an instance of {@link URL} that references a file in the
   * application's resources.
   *
   * @param path The full path (starting at the root), relative to the
   *             application or JAR file's resources directory.
   * @return A {@link URL} to the file or {@code null} if the path does not
   * point to a resource.
   */
  private URL getResourceUrl( final String path ) {
    return SvgRasterizer.class.getResource( path );
  }

  /**
   * Loads the resource specified by the given path into an instance of
   * {@link SVGDiagram} that can be rasterized into a bitmap format. The
   * {@link SVGUniverse} class will
   *
   * @param path The full path (starting at the root), relative to the
   *             application or JAR file's resources directory.
   * @return An {@link SVGDiagram} that can be rasterized onto a
   * {@link BufferedImage}.
   */
  private SVGDiagram loadDiagram( final String path ) {
    final var url = getResourceUrl( path );
    final var uri = sRenderer.loadSVG( url );
    final var diagram = sRenderer.getDiagram( uri );
    return applySettings( diagram );
  }

  /**
   * Instructs the SVG renderer to rasterize the image even if it would be
   * clipped.
   *
   * @param diagram The {@link SVGDiagram} to render.
   * @return The same instance with ignore clip heuristics set to {@code true}.
   */
  private SVGDiagram applySettings( final SVGDiagram diagram ) {
    diagram.setIgnoringClipHeuristic( true );
    return diagram;
  }

  /**
   * Scales the given source {@link Dimension} to the destination
   * {@link Dimension}, maintaining the aspect ratio with respect to
   * the best fit.
   *
   * @param src The original vector graphic dimensions to change.
   * @param dst The desired image dimensions to scale.
   * @return The given source dimensions scaled to the destination dimensions,
   * maintaining the aspect ratio.
   */
  private Dimension fit( final Dimension src, final Dimension dst ) {
    final var srcWidth = src.getWidth();
    final var srcHeight = src.getHeight();

    // Determine the ratio that will have the best fit.
    final var ratio = Math.min(
        dst.getWidth() / srcWidth, dst.getHeight() / srcHeight
    );

    // Scale both dimensions with respect to the best fit ratio.
    return new Dimension( (int) (srcWidth * ratio), (int) (srcHeight * ratio) );
  }
}
