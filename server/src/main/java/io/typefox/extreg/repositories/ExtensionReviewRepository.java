/********************************************************************************
 * Copyright (c) 2019 TypeFox
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 ********************************************************************************/
package io.typefox.extreg.repositories;

import org.springframework.data.repository.Repository;
import org.springframework.data.util.Streamable;

import io.typefox.extreg.entities.Extension;
import io.typefox.extreg.entities.ExtensionReview;

public interface ExtensionReviewRepository extends Repository<ExtensionReview, Long> {

    Streamable<ExtensionReview> findByExtension(Extension extension);

    long countByExtension(Extension extension);

}