package com.minelittlepony.client.util.render.plane;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.Direction;

import com.minelittlepony.client.util.render.Box;
import com.minelittlepony.client.util.render.Quad;
import com.minelittlepony.client.util.render.Vertex;

import javax.annotation.Nonnull;

public class ModelPlane extends Box<PlaneRenderer> {

    private Quad quad;

    public boolean hidden = false;

    public ModelPlane(PlaneRenderer renderer, int textureX, int textureY, float xMin, float yMin, float zMin, int w, int h, int d, float scale, Direction face) {
        super(renderer, textureX, textureY, xMin, yMin, zMin, w, h, d, scale, false);

        float xMax = xMin + w + scale;
        float yMax = yMin + h + scale;
        float zMax = zMin + d + scale;

        xMin -= scale;
        yMin -= scale;
        zMin -= scale;

        if (renderer.mirror) {
            float v = xMax;
            xMax = xMin;
            xMin = v;
        }

        if (renderer.mirrory) {
            float v = yMax;
            yMax = yMin;
            yMin = v;
        }

        if (renderer.mirrorz) {
            float v = zMax;
            zMax = zMin;
            zMin = v;
        }

        // w:west e:east d:down u:up s:south n:north
        Vertex wds = vert(xMin, yMin, zMin, 0, 0);
        Vertex eds = vert(xMax, yMin, zMin, 0, 8);
        Vertex eus = vert(xMax, yMax, zMin, 8, 8);
        Vertex wus = vert(xMin, yMax, zMin, 8, 0);
        Vertex wdn = vert(xMin, yMin, zMax, 0, 0);
        Vertex edn = vert(xMax, yMin, zMax, 0, 8);
        Vertex eun = vert(xMax, yMax, zMax, 8, 8);
        Vertex wun = vert(xMin, yMax, zMax, 8, 0);

        if (face == Direction.EAST) {
            quad = quad(textureX, d, textureY, h, edn, eds, eus, eun);
        }
        if (face == Direction.WEST) {
            quad = quad(textureX, d, textureY, h, wds, wdn, wun, wus);
        }
        if (face == Direction.UP) {
            quad = quad(textureX, w, textureY, d, edn, wdn, wds, eds);
        }
        if (face == Direction.DOWN) {
            quad = quad(textureX, w, textureY, d, eus, wus, wun, eun);
        }
        if (face == Direction.SOUTH) {
            quad = quad(textureX, w, textureY, h, eds, wds, wus, eus);
        }
        if (face == Direction.NORTH) {
            quad = quad(textureX, w, textureY, h, wdn, edn, eun, wun);
        }

        if (renderer.mirror || renderer.mirrory || renderer.mirrorz) {
            quad.flip();
        }
    }

    @Override
    public void render(@Nonnull BufferBuilder buffer, float scale) {
        if (!hidden) {
            quad.render(buffer, scale);
        }
    }
}