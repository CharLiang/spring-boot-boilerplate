/**
 * @(#)TreeNode.java, 2019-01-10.
 * <p>
 * .
 * .
 */
package com.youdao.sortinghat.data.common;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * TreeNode
 *
 * @author 
 *
 */
public class TreeNode<T extends TreeNode> {

    private long id;

    private List<T> children = Lists.newArrayList();

    public TreeNode() {
    }

    public TreeNode(long id) {
        this.id = id;
    }

    public TreeNode(long id, List<T> children) {
        this.id = id;
        this.children = children;
    }

    public void putChild(T child) {
        children.add(child);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}