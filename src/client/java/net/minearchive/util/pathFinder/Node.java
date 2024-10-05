package net.minearchive.util.pathFinder;

import net.minearchive.util.BlockUtils;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

public class Node {
    private final int x, y, z;
    private Node parent;
    private boolean blocked;
    private double g = 0, h = 0, cost = 0;

    public Node(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blocked = false;
    }

    public Node(int x, int y, int z, boolean blocked) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blocked = blocked;
    }

    public void setG(Node parent, double cost) {
        this.g = parent.getG() + cost;
        this.cost = cost;
    }

    public double calcG(Node parent) {
        return parent.getG() + getCost();
    }

    public void calcH(Node next) {
        h = (Math.abs(getX() - next.getX()) + Math.abs(getY() - next.getY())) + Math.abs(getZ() - next.getZ());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getCost() {
        return cost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Node add(Node node) {
        return new Node(x + node.x, y + node.y, z + node.z);
    }

    public Node up() {
        return new Node(x, y + 1, z);
    }

    public Node down() {
        return new Node(x, y - 1, z);
    }

    public BlockPos getAsBlockPos() {
        return new BlockPos(x, y, z);
    }

    @Override
    public String toString() {
        return "Node{" +
                "z=" + z +
                ", y=" + y +
                ", x=" + x +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x == node.x && y == node.y && z == node.z;
    }

    public static Node from(BlockPos pos) {
        return new Node(pos.getX(), pos.getY(), pos.getZ(), BlockUtils.equal(pos, Blocks.AIR));
    }

    public static Node from(Vec3d vec3d) {
        return new Node((int) vec3d.x, (int) vec3d.y, (int) vec3d.z, BlockUtils.equal(new BlockPos((int) vec3d.x, (int) vec3d.y, (int) vec3d.z), Blocks.AIR));
    }
}
