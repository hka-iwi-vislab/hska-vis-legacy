kubectl apply -f ./kubernetes/db-secret.yaml
kubectl apply -f ./kubernetes/config-map.yaml
kubectl apply -f ./kubernetes/mysql.yaml
kubectl apply -f ./kubernetes/category.yaml
kubectl apply -f ./kubernetes/product.yaml
kubectl apply -f ./kubernetes/user.yaml
kubectl apply -f ./kubernetes/reverse-proxy.yaml


