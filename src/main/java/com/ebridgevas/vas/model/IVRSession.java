package com.ebridgevas.vas.model;

//import com.ebridgevas.vas.dto.Authorisation;

/**
 * @author david@tekeshe.com
 */
public class IVRSession {

    private final Long uuid;
    // private final Authorisation authorisation;
    private TreeNode<NodeData> treeNode;

    public IVRSession(Long uuid,
                      /* Authorisation authorisation, */
                      TreeNode<NodeData> treeNode) {
        this.uuid = uuid;
        // this.authorisation = authorisation;
        this.treeNode = treeNode;
    }

    public Long getUuid() {
        return uuid;
    }

//    public Authorisation getAuthorisation() {
//        return authorisation;
//    }

    public TreeNode<NodeData> getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(TreeNode<NodeData> treeNode) {
        this.treeNode = treeNode;
    }
}
