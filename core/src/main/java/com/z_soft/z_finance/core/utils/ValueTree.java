package com.z_soft.z_finance.core.utils;

import com.z_soft.z_finance.core.interfaces.TreeNode;

import java.util.List;

// построитель дерева
public class ValueTree<T extends TreeNode> {

    // встраивает новый элемент в нужное место дерева: суть в том, что нужно найти родительский элемент для объекта newNode
    public void addToTree(long parentId, T newNode, List<T> nodeList) {
        if (parentId!=0){
            for (T currentNode: nodeList) {// искать сначала во всех корневых объектах
                if (currentNode.getId()==parentId){
                    currentNode.add(newNode);
                    return;
                }else {// если среди корневых элементов не найдены родители
                    TreeNode node = recursiveSearch(parentId, currentNode);// проходим по всем уровням дочерних элементов
                    if (node!=null){// если нашли среди дочерних элементов
                        node.add(newNode);
                        return;
                    }
                }
            }
        }

        nodeList.add(newNode);// если не найден родительский элемент - добавляем как корневой
    }


    // рекурсивно проходит по всем дочерним элементам
    private T recursiveSearch(long parentId, T child) {
        for (T node : (List<T>)child.getChilds()) {
            if (node.getId() == parentId) {
                return node;
            } else if (node.hasChilds()) {// если у текущего узло есть свои дочерние элемента - проходим и по ним
                recursiveSearch(parentId, node);
            }
        }
        return null;
    }
}