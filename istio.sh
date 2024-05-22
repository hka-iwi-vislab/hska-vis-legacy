kubectl create namespace istio-system
helm install istio-base istio/base -n istio-system --set defaultRevision=default
helm install istiod istio/istiod -n istio-system --wait
kubectl create namespace istio-ingress
helm install istio-ingress istio/gateway -n istio-ingress --wait
