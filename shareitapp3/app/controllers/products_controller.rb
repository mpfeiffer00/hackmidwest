class ProductsController < ApplicationController
  def new
    @product = Product.new
    @product.upc = params[:upc]
  end

  # POST /products/get_barcode
  def get_barcode
    @product = Product.find_or_initialize_by(upc: params[:upc])
    unless @product.new_record?
      redirect_to @product
    else
      redirect_to new_product_path(upc: params[:upc])
    end
  end
end
