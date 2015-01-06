package com.ese.beans;

import com.ese.model.db.MenuObjectModel;
import com.ese.service.LoginService;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ManagedBean(name = "index")
@ViewScoped
public class IndexBean extends Bean{
    private static final long serialVersionUID = 4112578634029874840L;
    @ManagedProperty("#{loginService}") private LoginService loginService;
    @ManagedProperty("#{msg}") private MessageBean msg;


    private String messageIndex;
    private TreeNode root;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
//        if(preLoad()){
            init();
//        }
    }

    private void init(){
        messageIndex = "HELLO!!!!!!!!!";
        root = createDocuments2();
    }

    private TreeNode createDocuments2() {
        TreeNode root = new DefaultTreeNode(new Document("Files", "-", "Folder"), null);
        List<MenuObjectModel> menuObjectModelList = loginService.getAllMenuObject();
        final int MENU = 1;
        final int TAB = 2;
        final int ACTION = 3;

        Map<Integer, DefaultTreeNode> treeNodeMap = new HashMap<Integer, DefaultTreeNode>();
        for (MenuObjectModel model : menuObjectModelList) {
            if ( !Utils.isNull(model.getObjCategory()) ) {
                if ( !Utils.isZero(Utils.parseInt(model.getCode(), 0)) && model.getObjCategory() == MENU ) {
                    treeNodeMap.put(model.getId(), new DefaultTreeNode(new Document(model.getName(), "", ""), root));
                } else if ( model.getObjCategory() == TAB ) {
                    treeNodeMap.put(model.getId(), new DefaultTreeNode(new Document("", model.getName(), ""), treeNodeMap.get(model.getParentId())));
                } else if ( model.getObjCategory() == ACTION ) {
                    new DefaultTreeNode(new Document("", "", model.getName()), treeNodeMap.get(model.getParentId()));
                }
            }
        }

        return root;
    }

}
