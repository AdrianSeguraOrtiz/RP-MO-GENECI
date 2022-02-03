parallel_inference() {
	technique=$1
	in_file=$2
    data_id=$(basename ${in_file%.*})

    mkdir -p "../inferred_networks/$data_id/"
    out_id="../inferred_networks/$data_id/GRN_$(basename $technique)"
    echo $out_id
    # Rscript $technique $in_file $out_file
}
export -f parallel_inference

techniques=$(ls *.R)
files=$(ls ../data/*/EXP/*.csv)

parallel parallel_inference ::: ${techniques[@]} ::: ${files[@]}
